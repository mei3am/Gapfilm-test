package com.github.mei3am.test.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.github.mei3am.test.view.fragments.MainFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule{

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

//    @ContributesAndroidInjector
//    abstract fun contribute():


}


