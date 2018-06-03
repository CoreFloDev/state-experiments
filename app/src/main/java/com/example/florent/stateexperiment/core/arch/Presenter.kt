package com.example.florent.stateexperiment.core.arch

import android.arch.lifecycle.LifecycleObserver
import io.reactivex.disposables.Disposable

abstract class Presenter<V : PresenterView> : LifecycleObserver {

    private var disposable: Disposable? = null

    fun attach(view: V) {
        onAttach(view)
    }

    fun detach() {
        disposable?.dispose()
    }

    protected fun add(disposable: Disposable) {
        this.disposable = disposable
    }

    protected abstract fun onAttach(view: V)

}
