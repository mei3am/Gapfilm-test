package com.github.mei3am.test.utils.interceptors


import com.github.mei3am.test.utils.Klog
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.util.concurrent.TimeUnit

class LogInterceptor : Interceptor {

    private val charset = Charsets.UTF_8
    private val requestUpLine = " ─.─.─.─.─.─ Request ─.─.─.─.─.──.─.─"
    private val endLine = "─.─.─.─.─.──.─.─.─.─.──.─.─.─.─.──.─.─.─.─.──"
    private val responseUpLine = "─.─.─.─.─.─ Response ─.─.─.─.─.──.─.─.─"
    private val end = "─.─.─.─.─.─ END "
    private val byteBody = "-byte body)"
    private val byteBodyOmitted = "-byte body omitted)"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {


        val request = chain.request()

        val requestBody = request.body()
        val hasRequestBody = requestBody != null


        var requestStartMessage = ("--> "
                + request.method()
                + ' '.toString() + request.url())

        if (hasRequestBody) {
            requestStartMessage += " (" + requestBody!!.contentLength() + byteBody
        }
        Klog.http(requestUpLine)
        Klog.http(requestStartMessage)


        if (hasRequestBody) {
            Klog.http("Content-Type: " + requestBody?.contentType())

            Klog.http("Content-Length: " + requestBody?.contentLength())
        }

        val headers = request.headers()
        run {
            var i = 0
            val count = headers.size()
            while (i < count) {
                val name = headers.name(i)
                // Skip headers from the request body as they are explicitly Klog.httpged above.
                if (!"Content-Type".equals(
                        name,
                        ignoreCase = true
                    ) && !"Content-Length".equals(name, ignoreCase = true)
                ) {
                    Klog.http(name + ": " + headers.value(i))
                }
                i++
            }
        }

        if (!hasRequestBody) {
            Klog.http(end + request.method())
            Klog.http(endLine)
        } else if (bodyEncoded(request.headers())) {
            Klog.http(end + request.method() + " (encoded body omitted)")
            Klog.http(endLine)
        } else {
            val buffer = Buffer()
            requestBody!!.writeTo(buffer)

            Klog.http("")
            if (isPlaintext(buffer)) {
                Klog.http(buffer.readString(charset))
                Klog.http(
                    end + request.method()
                            + " (" + requestBody.contentLength() + byteBody
                )
                Klog.http(endLine)
            } else {
                Klog.http(
                    end + request.method() + " (binary "
                            + requestBody.contentLength() + byteBodyOmitted
                )
                Klog.http(endLine)
            }
        }

        val startNs = System.nanoTime()
        val response: Response

        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            Klog.http(responseUpLine)
            Klog.http("<-- HTTP FAILED: $e")
            Klog.http(endLine)
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body()
        val contentLength = responseBody!!.contentLength()

        val bodySize = if (!(contentLength.equals(-1))) "$contentLength-byte" else "unknown-length"
        Klog.http(responseUpLine)
        Klog.http(
            "<-- "
                    + response.code()
                    + (if (response.message().isEmpty()) "" else ' ' + response.message())
                    + ' '.toString() + response.request().url()
                    + " (" + tookMs + "ms" + ", $bodySize body" + ')'.toString()
        )

        val headersResponse = response.headers()
        var i = 0
        val count = headersResponse.size()
        while (i < count) {
            Klog.http(headersResponse.name(i) + ": " + headersResponse.value(i))
            i++
        }

        if (!HttpHeaders.hasBody(response)) {
            Klog.http("<-- END HTTP")
            Klog.http(endLine)
        } else if (bodyEncoded(response.headers())) {
            Klog.http("<-- END HTTP (encoded body omitted)")
            Klog.http(endLine)
        } else {
            val source = responseBody.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()



            if (!isPlaintext(buffer)) {
                Klog.http("")
                Klog.http("""<-- END HTTP (binary ${buffer.size()}$byteBodyOmitted""")
                Klog.http(endLine)
                return response
            }

            if (contentLength != 0L) {
                Klog.http("")
                Klog.http(buffer.clone().readString(charset))
            }

            Klog.http("<-- END HTTP (" + buffer.size() + byteBody)
            Klog.http(endLine)
        }

        return response
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return contentEncoding != null && !"identity".equals(contentEncoding, ignoreCase = true)
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private fun isPlaintext(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = if (buffer.size() < 64) buffer.size() else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (e: EOFException) {
            return false // Truncated UTF-8 sequence.
        }
    }
}