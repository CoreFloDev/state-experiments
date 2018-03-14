package com.example.florent.stateexperiment.core.injection

import android.content.Context
import dagger.Component

@CoreScope
@Component(modules = [CoreModule::class])
interface CoreComponent {

    fun context() : Context

}
