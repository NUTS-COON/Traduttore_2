package ru.nuts_coon.traduttore

import io.reactivex.schedulers.Schedulers


class AppModel {

    private val apiKey = "trnsl.1.1.20170712T151942Z.db9262f613260602.4ec133e357fa30948db5d285b3dd576169e457d8"

    fun getTranslate(text: String, lang: String): io.reactivex.Observable<Answer> {
        val api = RetrofitAPI.retrofit.create(RetrofitAPI::class.java)
        return api.getAnswer(apiKey, text, lang)
                .subscribeOn(Schedulers.io())

    }
}