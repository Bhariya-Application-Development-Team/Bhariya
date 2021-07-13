package com.sudhir.bhariya

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.sudhir.bhariya.Repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var etNewpassword : EditText
    private lateinit var etConfirmpassword : EditText
    private lateinit var btnContinue : Button
    var phonenumber : String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val intent = intent
        phonenumber = intent.getStringExtra("phonenumber").toString()


        etNewpassword = findViewById(R.id.etNewpassword)
        etConfirmpassword = findViewById(R.id.etConfirmpassword)
        btnContinue = findViewById(R.id.btnContinue)

        btnContinue.setOnClickListener{
            if (validation()) {
                changePassword(etNewpassword.text.toString(), phonenumber)
                Toast.makeText(this, "Password Change Successful", Toast.LENGTH_SHORT).show()
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validation(): Boolean {
        if(TextUtils.isEmpty(etNewpassword.text)){
            etNewpassword.focusable
            etNewpassword.setError("Please Enter New Password")
            return false
        }

        if(TextUtils.isEmpty(etConfirmpassword.text)){
            etConfirmpassword.focusable
            etConfirmpassword.setError("Please Enter Confirm Password")
            return false
        }
        if(etConfirmpassword.text.toString() != etNewpassword.text.toString())
        {
            etConfirmpassword.setError("Password and Confirm Password must be same")
            return false
        }

        return true

    }

    private fun changePassword(password : String, phonenumber : String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var userRepository = UserRepository()
                var response = userRepository.changePassword(phonenumber, password)

                if(response.success==true){
                    val intent = Intent(this@ChangePasswordActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                else{
                    println("Error Updating the Password")
                }
            }
            catch(err: Exception){
                println(err.toString())
            }
        }
    }

}