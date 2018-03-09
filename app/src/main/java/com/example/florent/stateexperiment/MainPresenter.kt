package com.example.florent.stateexperiment

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class MainPresenter(
        private val repository: MainRepository
) {

    private val disposables = CompositeDisposable()
    private val input: Subject<MainAction> = PublishSubject.create()
    private val output: Observable<MainUiModel>

    init {
        output = input.publish { it.ofType(MainAction.Refresh::class.java).compose(networkCall(repository)) }
                .share()
                .cache()
    }

    companion object {
        fun networkCall(repository: MainRepository): ObservableTransformer<MainAction.Refresh, MainUiModel> {
            return ObservableTransformer {
                it.flatMap {
                    repository.networkCall()
                            .map { MainUiModel.Display(it) as MainUiModel }
                            .startWith(MainUiModel.Refreshing)
                }
            }
        }
    }

    fun attach(view: View) {
        view.inputs().subscribe(input)

        disposables.add(output
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::render))
    }

    fun detach() {
        disposables.clear()
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
