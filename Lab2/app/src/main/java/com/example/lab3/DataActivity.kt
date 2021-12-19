package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab2.R

class DataActivity : AppCompatActivity() {


    private val db = DataBaseHelper(this)
    private val travelID= ArrayList<String>()
    private val travelFrom= ArrayList<String>()
    private val travelTo= ArrayList<String>()
    private val travelTime= ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        val recyclerView: TextView = findViewById(R.id.textView2)
        recyclerView.movementMethod = ScrollingMovementMethod()
        var text = ""
        storeDataInArrays()

        for (i in travelID.indices){
            text += "${travelID[i]} - from ${travelFrom[i]} to ${travelTo[i]} at ${travelTime[i]}\n"
        }
        val closeDataBaseButton = findViewById<Button>(R.id.closeButton)
        recyclerView.text = text
        closeDataBaseButton.setOnClickListener{
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }
    }

    fun storeDataInArrays(){
        val cursor = db.readAllData()
        if (cursor != null){
            while(cursor.moveToNext()){
                travelID.add(cursor.getString(0))
                travelFrom.add(cursor.getString(1))
                travelTo.add(cursor.getString(2))
                travelTime.add(cursor.getString(3))

            }
        }
    }
}