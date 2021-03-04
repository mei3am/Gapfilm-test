package com.github.mei3am.test.utils

import com.github.mei3am.test.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree

object Klog {
    fun e(vararg o: Any?) {
        Timber.e("app Log : %s", *o)
    }

    fun d(vararg o: Any?) {
        Timber.d("app Log : %s ", *o)
    }

    fun http(vararg o: Any?) {
        Timber.v("%s", *o)
    }

    fun init() {
        if (BuildConfig.DEBUG) Timber.plant(DebugTree()) else Timber.plant(NotLoggingTree())
    }

    class NotLoggingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
            // just an empty block
        }
    }
}