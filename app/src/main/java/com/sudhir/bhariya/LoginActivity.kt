package com.sudhir.bhariya

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.sudhir.bhariya.Repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginActivity : AppCompatActivity() {

    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    private lateinit var btnLogin: ImageView
    private lateinit var etphonenumber: EditText
    private lateinit var etpassword: EditText
    private lateinit var tvSignup: TextView
    private lateinit var phonenumbertxt: TextInputLayout
    private lateinit var passwordtxt : TextInputLayout
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)

        etphonenumber = findViewById(R.id.etphonenumber)
        etpassword = findViewById(R.id.etpassword)
        btnLogin = findViewById(R.id.btnlogin)
        tvSignup = findViewById(R.id.tvSignup)
        phonenumbertxt = findViewById(R.id.phonenumbertxt)
        passwordtxt = findViewById(R.id.passwordtxt)
        // checkRunTimePermission()

        btnLogin.setOnClickListener {
            login()
            val etpassword : String = etpassword.text.toString()
            validatePhonenumber()
            validatePassword(etpassword)



        }

        tvSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkRunTimePermission() {
        if (!hasPermission()) {
            requestPermission()
        }
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@LoginActivity, permissions, 1)
    }

    private  fun validatePhonenumber(): Boolean{

        val phone : String = etphonenumber.text.toString()

        if(phone.isEmpty()){

            phonenumbertxt.setError("Enter your Phone Number.")
            return false
        }
        else{
            phonenumbertxt.setError(null)
            return true

        }
    }

    private fun validatePassword(etpassword : String):Boolean{


        if(etpassword.isEmpty()){

            passwordtxt.setError("Enter the correct password.")
            return false
        }
        else{
            passwordtxt.setError(null)
            return true

        }
    }

    private fun login() {

        val phonenumber = etphonenumber.text.toString()
        val password = etpassword.text.toString()



        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.checkUser(phonenumber, password)
                if (response.success == true) {
                    println("Successful Login")
                    // Open Dashboard
                    ServiceBuilder.token = "Bearer ${response.token}"
                    startActivity(
                        Intent(

                            this@LoginActivity,
                            MainActivity::class.java
                        )
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        val snack =
                            Snackbar.make(
                                linearLayout,
                                "Invalid credentials",
                                Snackbar.LENGTH_LONG
                            )
                        snack.setAction("OK", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()
                    }
                }


            } catch (ex: Exception) {
                println(ex.toString())
            }
        }
    }
}


