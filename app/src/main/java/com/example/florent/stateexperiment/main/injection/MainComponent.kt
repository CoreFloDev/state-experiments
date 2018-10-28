package com.example.florent.stateexperiment.main.injection

import com.example.florent.stateexperiment.core.injection.CoreComponent
import com.example.florent.stateexperiment.main.MainActivity
import dagger.Component

@MainScope
@Component(modules = [(MainModule::class)],
        dependencies = [(CoreComponent::class)])
interface MainComponent {

    fun inject(mainActivity: MainActivity)

}
