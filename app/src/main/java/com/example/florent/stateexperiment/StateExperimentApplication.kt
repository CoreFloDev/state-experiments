package com.example.florent.stateexperiment

import android.app.Application
import com.example.florent.stateexperiment.core.injection.CoreComponent
import com.example.florent.stateexperiment.core.injection.CoreModule
import com.example.florent.stateexperiment.core.injection.DaggerCoreComponent

class StateExperimentApplication : Application() {

    lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()
        coreComponent = DaggerCoreComponent.builder()
                .coreModule(CoreModule(this.applicationContext))
                .build()
    }

}
