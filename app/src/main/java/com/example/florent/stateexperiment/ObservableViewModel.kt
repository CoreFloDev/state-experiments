package com.example.florent.stateexperiment

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

abstract class ObservableViewModel<Input, Output> : ViewModel() {
    private val input: Subject<Input> = PublishSubject.create()
    private var output: Observable<Output>? = null

    fun caching(
            source: Observable<Input>,
            init: Observable<Input>.() -> Observable<Output>
    ): Observable<Output> {
        source.subscribe(input)

        if (output == null) {
            output = input.init().replay(1).autoConnect()
        }

        return output!!
    }
}
