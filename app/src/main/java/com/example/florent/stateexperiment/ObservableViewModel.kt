package com.example.florent.stateexperiment

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

abstract class ObservableViewModel<Input, Output> : ViewModel() {
    private val input: Subject<Input> = PublishSubject.create()
    private var output: Observable<Output>? = null

    protected fun caching(
            source: Observable<Input>,
            init: ObservableTransformer<Input, Output>
    ): Observable<Output> {
        source.subscribe(input)

        if (output == null) {
            output = input.compose(init).replay(1).autoConnect()
        }

        return output!!
    }
}
