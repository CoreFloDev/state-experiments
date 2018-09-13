package com.example.florent.stateexperiment.core.arch

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent

class LifecyclePresenter<V : PresenterView>(
        private val view: V,
        private val presenter: Presenter<V>
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun attach() {
        presenter.attach(view)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun detach() {
        presenter.detach()
    }

}
