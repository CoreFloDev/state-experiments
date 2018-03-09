package com.example.florent.stateexperiment

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

    private var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Injector.presenter == null) {
            Injector.presenter = MainPresenter(MainRepository())
            println("Singleton initialisation")
        }

        presenter = Injector.presenter

        presenter?.attach(this)
    }

    override fun onDestroy() {
        presenter?.detach()
        if (isFinishing) {
            Injector.presenter = null
        }
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
