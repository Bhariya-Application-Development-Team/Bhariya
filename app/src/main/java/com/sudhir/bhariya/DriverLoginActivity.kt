package com.sudhir.bhariya

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.sudhir.bhariya.NotificationClass.FirebaseService
import com.sudhir.bhariya.Repository.DriverRepository
import com.sudhir.bhariya.Repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Pair as UtilPair



class DriverLoginActivity : AppCompatActivity() {

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
    private lateinit var tvSignupdriver: TextView
    private lateinit var textdriver : TextView
    private lateinit var textuserlogin: TextView
    private lateinit var tvloginuser : TextView




    private lateinit var phonenumbertxt: TextInputLayout
    private lateinit var passwordtxt : TextInputLayout
    private lateinit var linearLayout: LinearLayout

    var firebaseToken = ""
    var database = FirebaseDatabase.getInstance();
    var reference = database.getReference("users")

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_driver_login)


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
        tvSignupdriver = findViewById(R.id.tvSignupdriver)
        textdriver = findViewById(R.id.textdriver)
        textuserlogin = findViewById(R.id.textuserlogin)
        tvloginuser = findViewById(R.id.tvloginuser)

        reference = FirebaseDatabase.getInstance().getReference("drivers")


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
        tvloginuser.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
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
        tvSignupdriver.setOnClickListener {
            val intent = Intent(LoginActivity@this, SignupDriverActivity::class.java)

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
        ActivityCompat.requestPermissions(this@DriverLoginActivity, permissions, 1)
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
        UpdateDriverToken()

        val phonenumber = etphonenumber.text.toString()
        val password = etpassword.text.toString()



        CoroutineScope(Dispatchers.IO).launch {
            try {
//                val repository = UserRepository()
                val repository = DriverRepository()

                val response = repository.checkDriver(phonenumber, password)
                if (response.success == true) {
                    println("Successful Login")
                    // Open Dashboard
                    ServiceBuilder.token = "Bearer ${response.token}"
                    val driver = repository.viewDriver()

                    startActivity(
                        Intent(
                            this@DriverLoginActivity,
                            SharedPreferenceActivity::class.java
                        )
                            .putExtra("phonenumber",phonenumbertxt.toString())

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

   fun UpdateDriverToken() {
        val phonenumber = etphonenumber.text.toString()
//        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isComplete) {
                firebaseToken = it.result
                Log.e("My token is ", firebaseToken)
                reference.child(phonenumber).child("token").setValue(firebaseToken)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT)

                    }.addOnFailureListener  {
                        Toast.makeText(this, "Failed to Update", Toast.LENGTH_LONG)
                    }
            }
        }
    }
}


