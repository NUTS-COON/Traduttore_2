package ru.nuts_coon.traduttore


import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    companion object {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://translate.yandex.net/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @GET("/api/v1.5/tr.json/translate")
    fun getAnswer(@Query("key") key: String, @Query("text") text: String, @Query("lang") lang: String): io.reactivex.Observable<Answer>
}