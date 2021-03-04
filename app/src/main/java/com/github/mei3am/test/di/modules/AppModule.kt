package com.github.mei3am.test.di.modules

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides

@Module(includes = [ViewModelModule::class, NetworkModule::class])
open class AppModule{

    @Provides
    fun resources(application: Application): Resources{
        return application.resources
    }
}