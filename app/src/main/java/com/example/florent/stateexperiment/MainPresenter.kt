package com.example.florent.stateexperiment

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class MainPresenter : ObservableViewModel<MainAction, MainUiModel>() {

    // This will ideally instantiated through dagger/similar
    private val repository = MainRepository()

    private var disposable: Disposable? = null

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

    fun attach(view: MainPresenter.View): Disposable {
        return caching(view.inputs(), ObservableTransformer {
            it.publish {
                it.ofType(MainAction.Refresh::class.java).compose(networkCall(repository))
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::render)
    }

    fun detach() {
        disposable?.dispose()
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
