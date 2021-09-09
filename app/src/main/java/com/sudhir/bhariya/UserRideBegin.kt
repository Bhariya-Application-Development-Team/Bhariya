package com.sudhir.bhariya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.sudhir.bhariya.Repository.DriverRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class UserRideBegin : AppCompatActivity() {
    private lateinit var txt_cancel_ride : TextView
    private lateinit var txt_driver_name : TextView
    private lateinit var txt_driver_phonenumber : TextView
    private lateinit var btn_esewa : Button
    private lateinit var btn_cash : Button
    private lateinit var layout_begin : View
    private lateinit var layout_end : View
    var driver_name : String? = null
    var driver_phone_number : String? = null
    var phonenumber : String? = null
    var total_fare : String? = null
    var startaddress : String? = null
    var endaddress : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_ride_begin)

        txt_cancel_ride = findViewById(R.id.txt_ride_cancel)
        txt_driver_name = findViewById(R.id.txt_driver_name)
        txt_driver_phonenumber = findViewById(R.id.txt_driver_phonenumber)
        layout_begin = findViewById(R.id.layout_ride_started)
        layout_end = findViewById(R.id.layout_endride)
        btn_esewa = findViewById(R.id.btn_esewa)
        btn_cash = findViewById(R.id.btn_cash)


        txt_cancel_ride.setOnClickListener {

        }
        val intent = intent
        phonenumber = intent.getStringExtra("phonenumber").toString()
//        phonenumber = "9860859091"
//        phonenumber = "endride 2345 startpoint:balaju endpoint:vanasthali phonenumber:9812"
        var fare = ""
        fare = fare!!.substringAfter("endride ").substringBefore(" ")
        phonenumber = phonenumber!!.substringAfter("phonenumber:")
        startaddress = phonenumber!!.substringAfter("startpoint:").substringBefore(" ")
        endaddress = phonenumber!!.substringAfter("endpoint:").substringBefore(" ")
        if(phonenumber!=""){
            layout_end.visibility = View.VISIBLE
            layout_begin.visibility = View.GONE
        }
        else{
            layout_begin.visibility = View.VISIBLE
            layout_end.visibility = View.GONE
        }
        println("Phone number " + phonenumber + "fare $fare startaddress: $startaddress endaddress $endaddress")

        println("Driver Phone Number")
        println(driver_name)
//        if(phonenumber == "endride"){
//            layout_begin.visibility = View.GONE
//            layout_end.visibility = View.VISIBLE
//            btn_esewa.setText(fare)
//        }



        get_driver(phonenumber!!)

        btn_esewa.setOnClickListener {
            val intent = Intent()
            startActivity(
                Intent(
                    this@UserRideBegin,
                    EsewaActivity::class.java
                )
                    .putExtra("phonennumber", phonenumber)
                    .putExtra("startpoint", startaddress)
                    .putExtra("endpoint", endaddress)
                    .putExtra("fare", fare))

        }


            // Stuff that updates the UI











        }

    private fun get_driver(phonenumber: String) {
        CoroutineScope(Dispatchers.IO).launch {
             try{
                var repository = DriverRepository()
                var response = repository.retrieveDriver(phonenumber!!)
                if (response.success == true) {
                    //Populate the Widgets
                    driver_name = response.Fullname.toString()
                    driver_phone_number = response.PhoneNumber.toString()
                    runOnUiThread {
                        txt_driver_name.setText(response.Fullname.toString())
                        txt_driver_phonenumber.setText(response.PhoneNumber.toString())
                    }
                    println("####### Response #######")
                    println(response.Fullname.toString())
//                     txt_driver_phonenumber.text = response.PhoneNumber.toString()
                } else {
                    println("Driver Not Found!")
                }

            }catch (e : Exception){
                 println(e.toString())
             }

        }


        txt_driver_name.text = driver_name
        txt_driver_phonenumber.text = driver_phone_number

    }


}