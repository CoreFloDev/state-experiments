package com.example.florent.stateexperiment.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.florent.stateexperiment.StateExperimentApplication
import com.example.florent.stateexperiment.injection.DaggerMainComponent
import com.example.florent.stateexperiment.injection.MainComponent
import com.example.florent.stateexperiment.injection.MainModule

class MainRotationStateHolder(application: Application) : AndroidViewModel(application) {

    val mainComponent: MainComponent = DaggerMainComponent
            .builder()
            .coreComponent(getApplication<StateExperimentApplication>().coreComponent)
            .mainModule(MainModule())
            .build()

}
