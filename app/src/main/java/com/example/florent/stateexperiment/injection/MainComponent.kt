package com.example.florent.stateexperiment.injection

import com.example.florent.stateexperiment.MainActivity
import dagger.Component

@Component(modules = [(MainModule::class)])
interface MainComponent {

    fun inject(mainActivity: MainActivity)
}
