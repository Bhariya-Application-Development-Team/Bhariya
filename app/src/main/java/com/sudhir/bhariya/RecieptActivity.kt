package com.sudhir.bhariya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RecieptActivity : AppCompatActivity() {
    private lateinit var source: TextView
    private lateinit var destination : TextView
    private lateinit var Date : TextView
    private lateinit var totalCost : TextView
    private lateinit var refId : TextView
    private lateinit var vehicletype : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reciept)
        source = findViewById(R.id.source)
        destination = findViewById(R.id.destination)
        Date = findViewById(R.id.date)
        totalCost = findViewById(R.id.totalcost)
        refId = findViewById(R.id.referenceid)
        vehicletype = findViewById(R.id.vehicletype)

        val tSource = intent.getStringExtra("Source").toString()
        val tdestination = intent.getStringExtra("Destination").toString()
        val tdate = intent.getStringExtra("Date").toString()
        val tcost = intent.getStringExtra("cost").toString()
        val trefId = intent.getStringExtra("refid").toString()
        val tvehicletype = intent.getStringExtra("productname").toString()

        source.text= tSource
        destination.text = tdestination
        Date.text = tdate
        totalCost.text =tcost
        refId.text = trefId
        vehicletype.text = tvehicletype

    }
}