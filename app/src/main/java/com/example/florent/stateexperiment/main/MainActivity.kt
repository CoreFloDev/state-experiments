package com.example.florent.stateexperiment.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.florent.stateexperiment.R.layout
import com.example.florent.stateexperiment.main.MainAction.Refresh
import com.example.florent.stateexperiment.main.MainPresenter.View
import com.example.florent.stateexperiment.main.MainUiModel.Display
import com.example.florent.stateexperiment.main.MainUiModel.Refreshing
import com.jakewharton.rxbinding2.view.RxView
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
                .get(MainViewModel::class.java)
                .mainComponent
                .inject(this)

        presenter.attach(this)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun inputs(): Observable<MainAction> {
        return RxView.clicks(refresh)
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