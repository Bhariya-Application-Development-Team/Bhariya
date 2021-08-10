package com.sudhir.bhariya

import android.app.ActivityOptions
import android.content.Intent

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.sudhir.bhariya.Repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Pair as UtilPair



class LoginActivity : AppCompatActivity() {

    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    private lateinit var btnLogin: ImageView
    private lateinit var etphonenumber: EditText
    private lateinit var etpassword: EditText
    private lateinit var tvForgotpassword : TextView
    private lateinit var image : ImageView
    private lateinit var tvSignup: TextView
    private lateinit var textothers : TextView
    private lateinit var welcome : TextView
    private lateinit var hello : TextView


    private lateinit var phonenumbertxt: TextInputLayout
    private lateinit var passwordtxt : TextInputLayout
    private lateinit var linearLayout: LinearLayout

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)


        etphonenumber = findViewById(R.id.etphonenumber)
        etpassword = findViewById(R.id.etpassword)
        tvForgotpassword = findViewById(R.id.tvForgotpassword)
        btnLogin = findViewById(R.id.btnlogin)
        tvSignup = findViewById(R.id.tvSignup)
        image = findViewById(R.id.imagel)

        textothers = findViewById(R.id.textothers)
        phonenumbertxt = findViewById(R.id.phonenumbertxt)
        passwordtxt = findViewById(R.id.passwordtxt)
        hello = findViewById(R.id.hello)
        welcome = findViewById(R.id.welcome)
         checkRunTimePermission()

        btnLogin.setOnClickListener {
            login()
            val etpassword : String = etpassword.text.toString()
            validatePhonenumber()
            validatePassword(etpassword)
        }

        tvForgotpassword.setOnClickListener{
            val intent = Intent(this,ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        tvSignup.setOnClickListener {
            val intent = Intent(LoginActivity@this, SignUpActivity::class.java)

          val options :   ActivityOptions  = ActivityOptions.makeSceneTransitionAnimation(LoginActivity@this,
              UtilPair.create(image, "logo_image"),
              UtilPair.create(etphonenumber, "edit_trans"),
              UtilPair.create(etpassword, "edit_trans"),
              UtilPair.create(btnLogin, "btn_trans"),
              UtilPair.create(tvSignup, "btn_trans"),
              UtilPair.create(textothers, "already"),
              UtilPair.create(hello, "logo_text"),
              UtilPair.create(welcome, "logo_desc")
              )
            startActivity(intent, options.toBundle())
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

    private fun validatePassword(etpassword: String):Boolean{


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
                            SharedPreferenceActivity::class.java
                        ).putExtra("phonenumber", phonenumber)
                            .putExtra("password", password)
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


