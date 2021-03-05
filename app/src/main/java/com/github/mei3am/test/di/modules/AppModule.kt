package com.github.mei3am.test.di.modules

import android.app.Application
import android.content.res.Resources
import androidx.room.Room
import com.github.mei3am.test.repository.AppDb
import dagger.Module
import dagger.Provides

@Module(includes = [ViewModelModule::class, NetworkModule::class])
open class AppModule{

    @Provides
    fun resources(application: Application): Resources{
        return application.resources
    }

    @Provides
    fun databaseProvider(application: Application?): AppDb {
        return Room.databaseBuilder(application!!, AppDb::class.java, "appDb")
                .build()
    }
}