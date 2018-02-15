package com.promact.curcon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currency = arrayOf("Select Base Currency", "Indian Rupee", "US Dollar", "Malaysian Ringgit",
                            "Bahraini Dinar", "Russian Ruble", "Hungarian Forint",
                            "Japanese Yen", "Swiss Franc")

    private var currencySelected: String = "Select Base Currency"

    private var currencyList = arrayOf("Indian Rupee", "US Dollar", "Malaysian Ringgit",
            "Bahraini Dinar", "Russian Ruble", "Hungarian Forint",
            "Japanese Yen", "Swiss Franc")

    private var currencyCodeList = hashMapOf("Indian Rupee" to "INR", "US Dollar" to "USD",
                                                "Malaysian Ringgit" to "MYR", "Bahraini Dinar" to "BHD",
                                                "Russian Ruble" to "RUB", "Hungarian Forint" to "HUF",
                                                "Japanese Yen" to "JPY", "Swiss Franc" to "CHF")


    private var conversionRates = hashMapOf("Indian Rupee" to listOf(0.0155,0.0608,0.0058,0.8941,3.9019,1.6932,0.0145),
                                                    "US Dollar" to listOf(64.2903,3.9126,0.376,57.4481,250.8708,108.9071,0.9339),
                                                    "Malaysian Ringgit" to listOf(16.4344,0.2555,0.0961,14.6633,64.1226,27.8328,0.2388),
                                                    "Bahraini Dinar" to listOf(170.895,2.6595,10.4095,152.6186,666.9613,289.5963,2.4839),
                                                    "Russian Ruble" to listOf(1.1197,0.01742,0.0682,0.0065,4.3702,1.8977,0.0163),
                                                    "Hungarian Forint" to listOf(0.2561,0.0032,0.0156,0.0014,0.2288,0.4344,0.0037),
                                                    "Japanese Yen" to listOf(0.587,0.0091,0.0358,0.0034,0.5221,2.2851,0.0085),
                                                    "Swiss Franc" to listOf(68.7123, 1.0706,4.1875,0.4025,61.2213,267.7758,116.6162))

    private var currencyLabelIdList = arrayOf(R.id.currencyLabel1,R.id.currencyLabel2,
            R.id.currencyLabel3,R.id.currencyLabel4,
            R.id.currencyLabel5,R.id.currencyLabel6,
            R.id.currencyLabel7)

    private var currencyIdList = arrayOf(R.id.currency1,R.id.currency2,
            R.id.currency3,R.id.currency4,
            R.id.currency5,R.id.currency6,
            R.id.currency7)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCurrencySpinner()

        currencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Nothing Selected" , Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Log.d("val",currency[position])
                setCurrency(currency[position])
            }
        }

        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertMoney(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        clearText.setOnClickListener {
            currencySpinner.setSelection(0)
            input.setText("")
        }
    }

    private fun setCurrency(currencyType: String) {

        when (currencyType) {

            "Select Base Currency" -> {
                input.isEnabled = false
                currencySelected = "Select Base Currency"
                convertedAmount.visibility = View.GONE
            }
            else -> {
                input.isEnabled = true
                currencySelected = currencyType

                initializeCurrency()

                convertedAmount.visibility = View.VISIBLE
            }
        }
    }

    private fun initCurrencySpinner() {
        currencySpinner.adapter = ArrayAdapter(applicationContext, R.layout.spinner_item, currency)
    }

    private fun initializeCurrency(){
        var index = 0
        for(c in currencyList){
            if(c != currencySelected){
                findViewById<TextView>(currencyLabelIdList[index]).text = c
                findViewById<TextView>(currencyIdList[index]).text = "0 " + currencyCodeList[c]
                index += 1
            }
        }
    }

    private fun convertMoney(input: String) {

        if(currencySelected != "Select Base Currency") {
            if (input.isNullOrEmpty() || input == "0") {
                initializeCurrency()
            } else {
                var conversionValues = conversionRates[currencySelected]

                var index = 0
                for (c in currencyList) {
                    if (c != currencySelected) {
                        findViewById<TextView>(currencyLabelIdList[index]).text = c
                        findViewById<TextView>(currencyIdList[index]).text = "%.2f".format((input.toDouble() * conversionValues!![index])) + " " + currencyCodeList[c]
                        index += 1
                    }
                }
            }
        }
    }

}

