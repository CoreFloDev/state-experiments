package com.example.florent.stateexperiment

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MainRepository {

    fun networkCall(): Observable<String> {
        return Observable
                .fromCallable {
                    Thread.sleep(5000)
                    generateRandomPassword()
                }.subscribeOn(Schedulers.io())
    }

    // @author https://gist.github.com/Audhil/3e4332e14f0583062ead8147ab185d7b
    private fun generateRandomPassword(): String {
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var passWord = ""
        for (i in 0..31) {
            passWord += chars[Math.floor(Math.random() * chars.length).toInt()]
        }
        return passWord
    }


}
