package com.example.florent.stateexperiment.injection

import com.example.florent.stateexperiment.MainPresenter
import com.example.florent.stateexperiment.MainRepository
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainRepository() = MainRepository()

    @Provides
    fun provideMainPresenter(repository: MainRepository) = MainPresenter(repository)
}
