package com.example.florent.stateexperiment.core.arch

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

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
