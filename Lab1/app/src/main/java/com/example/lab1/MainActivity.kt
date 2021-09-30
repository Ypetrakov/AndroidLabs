package com.example.lab1

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textResult: TextView = findViewById(R.id.textView)

        val textView1: AutoCompleteTextView = findViewById(R.id.editFrom)
        val textView2: AutoCompleteTextView = findViewById(R.id.editTo)

        val radio: RadioGroup = findViewById(R.id.radio)


        val cities: Array<out String> = resources.getStringArray(R.array.cities_array)

            ArrayAdapter(this, android.R.layout.simple_list_item_1, cities).also {  adapter ->
            textView1.setAdapter(adapter)
            textView2.setAdapter(adapter)
        }

        lateinit var cityFrom: String
        lateinit var cityTo: String
        lateinit var time: String

        val submitButton: Button = findViewById(R.id.button)
        submitButton.setOnClickListener{
            cityFrom = textView1.text.toString()
            cityTo = textView2.text.toString()


            if (cityFrom in cities && cityTo in cities && radio.checkedRadioButtonId != -1) {
                time = findViewById<RadioButton>(radio.checkedRadioButtonId).text.toString()
                textResult.text = getString(R.string.train_message, cityFrom, cityTo, time)
            } else {
                textResult.text = getString(R.string.train_wrong_message)

            }
        }
    }

}
