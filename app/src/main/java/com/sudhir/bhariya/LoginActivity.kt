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
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvSignup: TextView
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etphonenumber)
        etPassword = findViewById(R.id.etpassword)
        btnLogin = findViewById(R.id.btnlogin)
        tvSignup = findViewById(R.id.tvSignup)
        // checkRunTimePermission()

        btnLogin.setOnClickListener {
            login()


        }

        tvSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

//        tvSignup.setOnClickListener {
//
//            startActivity(
//                Intent(
//                    this@LoginActivity,
//                    SignupActivity::class.java
//                )
//            )
//        }
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

    private fun login() {

        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.checkUser(username, password)
                if (response.success == true) {
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
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@LoginActivity,
                        ex.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

