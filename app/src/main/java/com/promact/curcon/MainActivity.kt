package com.promact.curcon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        convertMoney("")

        inputDollars.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertMoney(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        clearText.setOnClickListener {
            convertMoney("")
            inputDollars.setText("")
        }
    }

    private fun convertMoney(dollars: String) {

        if(dollars.isNullOrEmpty() || dollars == "0"){

            indianRupees.text = "0" + " RS"
            malaysianRinggit.text = "0" + " RM"
            bahrainiDinar.text = "0" + " BD"
            russianRuble.text = "0" + " RUB"
            hungarianForint.text = "0" + " HUF"
            japaneseYen.text = "0" + " JPY"
            swissFranc.text = "0" + " SFR"

        }else{

            val dollar = dollars.toDouble()

            indianRupees.text = "%.2f".format((dollar * 64.2903)) + " RS"
            malaysianRinggit.text = "%.2f".format((dollar * 3.9126)) + " RM"
            bahrainiDinar.text = "%.2f".format((dollar * 0.376)) + " BD"
            russianRuble.text = "%.2f".format((dollar * 57.4481)) + " RUB"
            hungarianForint.text = "%.2f".format((dollar * 250.8708)) + " HUF"
            japaneseYen.text = "%.2f".format((dollar * 108.9071)) + " JPY"
            swissFranc.text = "%.2f".format((dollar * 0.9339)) + " SFR"

        }
    }

}

