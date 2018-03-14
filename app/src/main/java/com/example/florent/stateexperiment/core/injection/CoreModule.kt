package com.example.florent.stateexperiment.core.injection

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class CoreModule(val context: Context) {

    @Provides
    @CoreScope
    fun provideContext() = context


}
