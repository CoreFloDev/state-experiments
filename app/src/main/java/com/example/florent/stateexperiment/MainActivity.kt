package com.example.florent.stateexperiment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.florent.stateexperiment.MainPresenter.View
import com.example.florent.stateexperiment.MainUiModel.Display
import com.example.florent.stateexperiment.MainUiModel.Refreshing
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View {

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val networkViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        presenter = MainPresenter(MainRepository(), networkViewModel)

        presenter.attach(this)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun inputs(): Observable<MainAction> {
        return RxView.clicks(refresh)
                .doOnNext { println("refresh clicked") }
                .map { MainAction.Refresh }
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
