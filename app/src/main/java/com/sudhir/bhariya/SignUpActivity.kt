package com.sudhir.bhariya

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Pair
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.sudhir.bhariya.Repository.UserRepository
import com.sudhir.bhariya.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import android.util.Pair as UtilPair

class SignUpActivity : AppCompatActivity() {
    private lateinit var  fullname : EditText
    private lateinit var  phonenumber : EditText
    private lateinit var  address : EditText
    private lateinit var  password : EditText
    private lateinit var confirmpass : EditText
    private lateinit var pnumtext: TextInputLayout
    private lateinit var fnametext : TextInputLayout
    private lateinit var  submit : ImageView
    private lateinit var addtext : TextInputLayout
    private lateinit var pwd : TextInputLayout
    private lateinit var login : TextView
    private lateinit var  passcon : TextInputLayout
    private lateinit var image : ImageView
    private lateinit var textothers : TextView
    private lateinit var hello : TextView
    private lateinit var welcome : TextView

//    private lateinit var adds : TextView
//    private lateinit var cpass : TextView
//    private lateinit var mainpwd : TextView
//    private lateinit var  nametext :TextView
//    private lateinit var  ptext : TextView

    private lateinit var phone : String
    private lateinit var name : String
    private lateinit var location : String
    private lateinit var paswd : String
    private lateinit var cpaswd : String
    lateinit var database: DatabaseReference




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_up)

        fullname = findViewById(R.id.fullname)
        phonenumber = findViewById(R.id.phonenumber)
        address = findViewById(R.id.address)
        password = findViewById(R.id.password)
        pnumtext = findViewById(R.id.pnumtext)
        fnametext = findViewById(R.id.fnametext)
        confirmpass = findViewById(R.id.confirmpassword)
        submit = findViewById(R.id.register)
        image = findViewById(R.id.imagel)
        addtext = findViewById(R.id.addtext)
        textothers = findViewById(R.id.textothers)
        login = findViewById(R.id.login)
        hello = findViewById(R.id.hello)
        welcome = findViewById(R.id.welcome)

        passcon = findViewById(R.id.passcon)
        database = FirebaseDatabase.getInstance().reference
//        adds = findViewById(R.id.adds)
//        cpass = findViewById(R.id.cpass)
//        mainpwd = findViewById(R.id.mainpwd)
//        ptext = findViewById(R.id.ptext)
//        nametext = findViewById(R.id.nametext)
        pwd = findViewById(R.id.pwd)

//        val builder = AlertDialog.Builder(this, R.style)

//        if(FirebaseAuth.getInstance().currentUser!!.phoneNumber != null &&
//                !TextUtils.isDigitsOnly(FirebaseAuth.getInstance().currentUser!!.phoneNumber))
//                    phonenumber.setText(FirebaseAuth.getInstance().currentUser!!.phoneNumber)
//
//        //View

        submit.setOnClickListener {

            cpaswd = confirmpass.text.toString()
            paswd = password.text.toString()
            validatePhone()
            validateName()
            validateAddress()
            validatePassword(paswd)
            validateConfirmPassword(cpaswd)

            if(validatePhone() == true && validateAddress() == true && validateName() == true && validateConfirmPassword(cpaswd)==true && validatePassword(paswd)){
                checkPassword(cpaswd, paswd)
            }

        }

        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            val options : ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this@SignUpActivity,
                UtilPair.create(image, "logo_image"),
                UtilPair.create(phonenumber, "edit_trans"),
                UtilPair.create(password, "edit_trans"),
                UtilPair.create(address, "edit_trans"),
                UtilPair.create(fullname, "edit_trans"),
                UtilPair.create(submit, "btn_trans"),
                UtilPair.create(login, "btn_trans"),
                UtilPair.create(textothers, "logo_text"),
                UtilPair.create(hello, "logo_text")
            )
            startActivity(intent, options.toBundle())
        }

    }



    private  fun validatePhone(): Boolean{

          phone  = phonenumber.text.toString()

        if(phone.isEmpty()){

            pnumtext.setError("Phone Number field cannot be empty.")
            return false
        }
        else{
            pnumtext.setError(null)
            return true

        }
    }

    private fun validateName():Boolean{
        name = fullname.text.toString()
        if(name.isEmpty()){

            fnametext.setError("Full name field cannot be empty.")
            return false
        }
        else{
            fnametext.setError(null)
            return true

        }
    }

    private fun validateAddress():Boolean{
       location = address.text.toString()
        if(location.isEmpty()){

            addtext.setError("Address field cannot be empty.")
            return false
        }
        else{
            addtext.setError(null)
            return true

        }
    }

    private fun validatePassword(password : String):Boolean{


        if(password.isEmpty()){

            pwd.setError("Password field cannot be empty.")
            return false
        }
        else{
            pwd.setError(null)
            return true

        }
    }

    private fun validateConfirmPassword(confirmpassword : String):Boolean{

        if(confirmpassword.isEmpty()){

            passcon.setError("Confirm Password field cannot be empty.")

            return false
        }
        else{
            passcon.setError(null)
            return true

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkPassword(confirmpassword : String, pass : String){
        if(confirmpassword != pass){
            val builder = AlertDialog.Builder(this)
            //set title for alert dialog
            builder.setTitle(R.string.dialogTitle)
            //set message for alert dialog
            builder.setMessage(R.string.dialogMessage)
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Cancel"){dialogInterface, which ->
               fullname.setText("")
                phonenumber.setText("")
                address.setText("")
                confirmpass.setText("")
                password.setText("")
                phonenumber.requestFocus()
            }
            //performing cancel action
            builder.setNeutralButton("OK"){dialogInterface , which ->

            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        else{
            register()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun register(){
        val customer =
            User(Phonenumber = phone, Fullname = name, Address = location, password = paswd)

        registerNewUser(phone,name, location,paswd)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.signupUser(customer)
                if (response.success == true) {

                    withContext(Dispatchers.Main) {
                        val options : ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this@SignUpActivity,
                            UtilPair.create(image, "logo_image"),
                            UtilPair.create(phonenumber, "edit_trans"),
                            UtilPair.create(password, "edit_trans"),
                            UtilPair.create(address, "edit_trans"),
                            UtilPair.create(fullname, "edit_trans"),
                            UtilPair.create(submit, "btn_trans"),
                            UtilPair.create(login, "btn_trans"),
                            UtilPair.create(textothers, "already"),
                            UtilPair.create(hello, "logo_text"),
                                    UtilPair.create(welcome, "logo_desc")

                        )
                        startActivity(

                            Intent(
                                this@SignUpActivity,
                                LoginActivity::class.java
                            ), options.toBundle())

                        Toast.makeText(
                            this@SignUpActivity,
                            "Registered Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                    }


                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SignUpActivity, ex.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }



    }

    fun registerNewUser(phonenumber : String, name : String, location: String, password : String) {
        val user = User(phonenumber, name, location, password)

        database.child("users").child(phonenumber).setValue(user)
    }




}