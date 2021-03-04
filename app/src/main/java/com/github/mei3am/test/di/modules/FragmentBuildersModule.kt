package com.github.mei3am.test.di.modules

import com.github.mei3am.test.view.fragments.ContentsFragment
import com.github.mei3am.test.view.fragments.FavoriteFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.github.mei3am.test.view.fragments.MainFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule{

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeContentsFragment(): ContentsFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteFragment(): FavoriteFragment

//    @ContributesAndroidInjector
//    abstract fun contribute():


}


