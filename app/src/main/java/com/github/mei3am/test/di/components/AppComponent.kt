package com.github.mei3am.test.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import com.github.mei3am.test.TestApp
import com.github.mei3am.test.di.modules.ActivityModule
import com.github.mei3am.test.di.modules.AppModule
import com.github.mei3am.test.di.modules.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        NetworkModule::class
    ]
)

interface AppComponent{
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(testApp: TestApp)
}

