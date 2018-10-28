package com.example.florent.stateexperiment.main

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.florent.stateexperiment.R.layout
import com.example.florent.stateexperiment.core.arch.LifecyclePresenter
import com.example.florent.stateexperiment.main.messages.MainAction.Refresh
import com.example.florent.stateexperiment.main.MainPresenter.View
import com.example.florent.stateexperiment.main.messages.MainUiModel.Display
import com.example.florent.stateexperiment.main.messages.MainUiModel.Refreshing
import com.example.florent.stateexperiment.main.messages.MainAction
import com.example.florent.stateexperiment.main.messages.MainUiModel
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), View {

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        ViewModelProviders.of(this)
                .get(MainRotationStateHolder::class.java)
                .mainComponent
                .inject(this)

        lifecycle.addObserver(LifecyclePresenter(this, presenter))
    }

    override fun inputs(): Observable<MainAction> {
        return refresh.clicks()
                .doOnNext { println("refresh clicked") }
                .map { Refresh }
    }

    override fun render(model: MainUiModel) {
        println("$model command received")
        when (model) {
            Refreshing -> {
                progress.visibility = VISIBLE
                result.text = ""
            }
            is Display -> {
                progress.visibility = GONE
                result.text = model.name
            }
        }
    }
}
