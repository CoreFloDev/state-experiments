package com.example.florent.stateexperiment.main

import com.example.florent.stateexperiment.main.MainAction.Refresh
import com.example.florent.stateexperiment.main.MainUiModel.Display
import com.example.florent.stateexperiment.main.MainUiModel.Refreshing
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observables.ConnectableObservable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject


class MainPresenter(
        private val repository: MainRepository
) {

    private lateinit var disposable: Disposable
    private val input: Subject<MainAction> = PublishSubject.create()
    private val output: ConnectableObservable<MainUiModel>

    init {
        output = input.publish { it.ofType(Refresh::class.java).compose(networkCall(repository)) }
                .replay(1)
    }

    companion object {
        fun networkCall(repository: MainRepository): ObservableTransformer<Refresh, MainUiModel> {
            return ObservableTransformer {
                it.flatMap {
                    repository.networkCall()
                            .map { Display(it) as MainUiModel }
                            .startWith(Refreshing)
                }
            }
        }
    }

    fun attach(view: View) {
        view.inputs().subscribe(input)

        disposable = output
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::render)

        output.connect()
    }

    fun detach() {
        disposable.dispose()
    }

    interface View {
        fun inputs(): Observable<MainAction>
        fun render(model: MainUiModel)
    }
}

sealed class MainAction {
    object Refresh : MainAction()
}

sealed class MainUiModel {
    object Refreshing : MainUiModel()
    data class Display(val name: String) : MainUiModel()
}
