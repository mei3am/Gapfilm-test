package com.github.mei3am.test.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.github.mei3am.test.di.ViewModelKey
import com.github.mei3am.test.viewModel.AppViewModelFactory
import com.github.mei3am.test.viewModel.ContentsViewModel
import com.github.mei3am.test.viewModel.FavoriteViewModel
import com.github.mei3am.test.viewModel.MainViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContentsViewModel::class)
    abstract fun bindContentsViewModel(viewModel: ContentsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(::class)
//    abstract fun bind(viewModel: ): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}