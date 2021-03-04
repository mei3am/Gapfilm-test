package com.github.mei3am.test.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.github.mei3am.test.view.activities.MainActivity

@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}