package ru.nuts_coon.traduttore

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers


class AppViewModel : ViewModel(){

    val appModel = AppModel()
    val translateLiveData = MutableLiveData<List<String?>>()
    val errorLiveData = MutableLiveData<Boolean>()

    fun translate(text: String, code: String, editText: String){

        appModel.getTranslate(text, code)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { errorLiveData.value = true }
                .subscribe {
                    translateLiveData.value = listOf(it.getText()?.first(), editText)
                    errorLiveData.value = false
                }
    }
}
