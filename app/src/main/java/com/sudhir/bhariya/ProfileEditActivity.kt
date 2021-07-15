package com.sudhir.bhariya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.textservice.TextInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.sudhir.bhariya.Repository.UserRepository
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.lang.Exception

class ProfileEditActivity : AppCompatActivity() {
    private lateinit var userImage : CircleImageView
    private lateinit var etfullname : EditText
    private lateinit var etphonenumber : EditText
    private lateinit var etaddress : EditText
    private lateinit var btnSave : Button
    var primary_phone : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
        val intent = intent

        retrieveValue()
        etfullname.setText(intent.getStringExtra("fullname"))
        etaddress.setText(intent.getStringExtra("address"))
        etphonenumber.setText(intent.getStringExtra("phonenumber"))
        primary_phone = intent.getStringExtra("phonenumber").toString()

        userImage.setOnClickListener{
            Toast.makeText(this, "Image Change Function Started", Toast.LENGTH_SHORT).show()
        }

        btnSave.setOnClickListener{
            if(validation()) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val repository = UserRepository().updateUser(
                            primary_phone,
                            etfullname.text.toString(),
                            etaddress.text.toString(),
                            etphonenumber.text.toString()
                        )
                        val response = repository
                        if(response.success==true){
                            println("Successfully Updated")
                            finish()
                            startActivity(Intent(this@ProfileEditActivity,ProfileActivity::class.java))

                        }
                        else{
                            println("Update Unsuccessful")
                        }
                    }
                    catch(ex : Exception){
                        println(ex)
                    }
                }
            }
        }





    }

    private fun retrieveValue(){
        userImage = findViewById(R.id.userimg)
        etfullname = findViewById(R.id.etfullname)
        etphonenumber = findViewById(R.id.etphonenumber)
        etaddress = findViewById(R.id.etaddress)
        btnSave = findViewById(R.id.btnSave)
    }

    private fun validation() : Boolean{
        val symbols = "0123456789/?!:;%"
        val numbers = "0123456789"

        if(TextUtils.isEmpty(etfullname.text)){
            etfullname.isFocusable
            etfullname.setError("Please Enter the Full Name")
            return false

        }
        if (etfullname!!.text.any {it in symbols}) {
            etfullname.setError("Invalid Input Detected!")
            return false

        }

        if(TextUtils.isEmpty(etaddress.text)){
            etaddress.isFocusable
            etaddress.setError("Please Enter the Address")
            return false

        }
        if (etaddress!!.text.any {it in symbols}) {
            etaddress.setError("Invalid Symbol Detected!")
            return false

        }

        if(TextUtils.isEmpty(etphonenumber.text)){
            etphonenumber.isFocusable
            etphonenumber.setError("Please Enter the Phone Number")
            return false
        }
        return true
    }


}