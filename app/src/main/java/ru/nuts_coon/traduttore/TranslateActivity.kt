package ru.nuts_coon.traduttore

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class TranslateActivity : AppCompatActivity() {

    var spinnerSelected1: Int = 1
    var spinnerSelected2: Int = 2
    lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inflateSpinner()
        btnSetText()
        setupViewModel()
        setupClickListener()

        tvInfo.visibility = View.INVISIBLE
    }

    private fun setupViewModel(){
        val context = this
        appViewModel = ViewModelProviders
                .of(this)
                .get(AppViewModel::class.java)
                .apply {
                    translateLiveData.observe(context, Observer {
                        if(it!!.contains("Direct")) setTranslate(it.first()!!, etDirect) else setTranslate(it.first()!!, etReverse)
                        tvInfo.visibility = View.VISIBLE
                    })
                    errorLiveData.observe(context, Observer {
                        if(it!!) showErrorMessage()
                })
        }
    }

    private fun showErrorMessage() {
        toast("Не удалось выполнить перевод")
    }

    private fun inflateSpinner() {
        val langList = resources.getStringArray(R.array.language)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, langList)

        spinnerDirect.adapter = adapter
        spinnerReverse.adapter = adapter

        spinnerDirect.setSelection(langList.indexOf("Английский"))
        spinnerReverse.setSelection(langList.indexOf("Русский"))

        spinnerSelected1 = spinnerDirect.selectedItemPosition
        spinnerSelected2 = spinnerReverse.selectedItemPosition
    }

    @SuppressLint("SetTextI18n")
    private fun btnSetText(){
        val langCode = resources.getStringArray(R.array.langCode)
        btnDirect.text = "${langCode[spinnerSelected1]}-${langCode[spinnerSelected2]}->"
        btnReverse.text = "<-${langCode[spinnerSelected2]}-${langCode[spinnerSelected1]}"
    }

    private fun setupClickListener(){
        btnDirect.setOnClickListener {
            translate(etDirect.text.toString(),
                    btnDirect.text.toString().substring(0, 5),
                    "Reverse")
        }

        btnReverse.setOnClickListener {
            translate(etReverse.text.toString(),
                    btnReverse.text.toString().substring(2),
                    "Direct")
        }

        spinnerDirect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerSelected1 = position
                btnSetText()
            }
        }
        spinnerReverse.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerSelected2 = position
                btnSetText()
            }
        }

        tvInfo.setOnClickListener {
            val uri = Uri.parse("https://translate.yandex.ru/m/translate")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private fun translate(text: String, code: String, editText: String){
        if (isOnline()) appViewModel.translate(text, code, editText) else showErrorMessage()
    }

    private fun setTranslate(text: String, editText: EditText){
        editText.setText(text)
    }

    private fun isOnline(): Boolean{
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}