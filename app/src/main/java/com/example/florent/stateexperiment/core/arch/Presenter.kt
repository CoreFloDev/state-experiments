package com.example.florent.stateexperiment.core.arch

import io.reactivex.disposables.Disposable

abstract class Presenter<V : PresenterView> {

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
