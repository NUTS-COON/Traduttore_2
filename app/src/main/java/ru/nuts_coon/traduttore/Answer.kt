package ru.nuts_coon.traduttore


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Answer {

    @SerializedName("code")
    @Expose
    private var code: Int = 0
    @SerializedName("lang")
    @Expose
    private var lang: String? = null
    @SerializedName("text")
    @Expose
    private var text: List<String>? = null

    fun getCode(): Int = code

    fun setCode(code: Int) {
        this.code = code
    }

    fun getLang(): String? = lang

    fun setLang(lang: String) {
        this.lang = lang
    }

    fun getText(): List<String>? = text

    fun setText(text: List<String>) {
        this.text = text
    }
}