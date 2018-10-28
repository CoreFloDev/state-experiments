package com.example.florent.stateexperiment.main

import com.example.florent.stateexperiment.core.arch.Presenter
import com.example.florent.stateexperiment.core.arch.PresenterView
import com.example.florent.stateexperiment.main.messages.MainAction
import com.example.florent.stateexperiment.main.messages.MainAction.Refresh
import com.example.florent.stateexperiment.main.messages.MainUiModel
import com.example.florent.stateexperiment.main.messages.MainUiModel.Display
import com.example.florent.stateexperiment.main.messages.MainUiModel.Refreshing
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observables.ConnectableObservable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject


class MainPresenter(
        private val repository: MainRepository
) : Presenter<MainPresenter.View>() {

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

    override fun onAttach(view: View) {
        add(output
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::render))

        output.connect()

        view.inputs().subscribe(input)
    }

    interface View : PresenterView {
        fun inputs(): Observable<MainAction>
        fun render(model: MainUiModel)
    }
}

