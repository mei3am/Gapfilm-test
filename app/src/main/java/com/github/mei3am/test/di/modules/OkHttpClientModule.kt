package com.github.mei3am.test.di.modules

import android.app.Application
import com.github.mei3am.test.BuildConfig
import com.github.mei3am.test.utils.interceptors.LogInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

@Module(includes = [AppModule::class])
class OkHttpClientModule {

    @Provides
    internal fun okHttpClient(cache: Cache, loggingInterceptor: LogInterceptor?): OkHttpClient {
        val okHttp =  OkHttpClient()
            .newBuilder()
            .cache(cache)
        loggingInterceptor?.let {
            okHttp.addInterceptor(loggingInterceptor)
        }

        return okHttp.build()
    }

    @Provides
    fun cache(file: File): Cache{
        return Cache(file, 5.toLong() * 1024 * 1024)
    }

    @Provides
    fun file(application: Application): File {
        val file = File(application.cacheDir, "HttpCache")
        file.mkdirs()
        return file
    }

    @Provides
    internal fun httpLoggingInterceptor(): LogInterceptor? {
        if (!BuildConfig.DEBUG){
            return null
        }
        return LogInterceptor()
    }
}