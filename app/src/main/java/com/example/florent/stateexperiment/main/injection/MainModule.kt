package com.example.florent.stateexperiment.injection

import com.example.florent.stateexperiment.main.MainPresenter
import com.example.florent.stateexperiment.main.MainRepository
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    @MainScope
    fun provideMainRepository() = MainRepository()

    @Provides
    @MainScope
    fun provideMainPresenter(repository: MainRepository) = MainPresenter(repository)
}
