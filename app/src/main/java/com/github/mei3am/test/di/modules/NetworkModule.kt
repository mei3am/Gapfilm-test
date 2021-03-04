package com.github.mei3am.test.di.modules

import dagger.Module
import dagger.Provides
import com.github.mei3am.test.apis.AppServices
import com.github.mei3am.test.constants.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [OkHttpClientModule::class])
class NetworkModule {

    @Provides
    fun endPoint(retrofit: Retrofit): AppServices {
        return retrofit.create(AppServices::class.java)
    }

    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}