package com.smithnielson.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.lang.Double.parseDouble
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {


    private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance()
    private val percentFormat: NumberFormat = NumberFormat.getPercentInstance()

    private var billAmount: Double = 0.0
    private var percent: Double = 0.15

    private var amountTextView: TextView? = null
    private var percentTextView: TextView? = null
    private var tipTextView: TextView? = null
    private var totalTextView: TextView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        amountTextView = findViewById(R.id.amountTextView)
        percentTextView = findViewById(R.id.percentTextView)
        tipTextView = findViewById(R.id.tipTextView)
        totalTextView = findViewById(R.id.totalTextView)


        tipTextView?.text = currencyFormat.format(0)
        totalTextView?.text = currencyFormat.format(0)

        val amountEditText: EditText = findViewById(R.id.amountEditText)
        amountEditText.addTextChangedListener(amountEditTextWatcher)

        val percentSeekBar: SeekBar = findViewById(R.id.percentSeekBar)
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener)

    }



    private fun calculate(){
        percentTextView?.text = percentFormat.format(percent)
        val tip:Double = billAmount * percent
        val total:Double = billAmount + tip
        tipTextView?.text = currencyFormat.format(tip)
        totalTextView?.text = currencyFormat.format(total)
    }


    private val seekBarListener:SeekBar.OnSeekBarChangeListener = object:SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            percent = p1 / 100.0
            calculate()

        }
        override fun onStartTrackingTouch(p0: SeekBar?) {}
        override fun onStopTrackingTouch(p0: SeekBar?) {}
    }




    private val amountEditTextWatcher: TextWatcher = object:TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            try {
                billAmount  = parseDouble(p0.toString()) / 100.0
                amountTextView?.text = (currencyFormat.format(billAmount)) // formatting bill amt as currency
            } catch (e:NumberFormatException){
                amountTextView?.text = ""
                billAmount = 0.0
            }
            calculate()

        }
        override fun afterTextChanged(p0: Editable?) {
        }
    }

}