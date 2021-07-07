package com.sudhir.bhariya

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout

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

//    private lateinit var adds : TextView
//    private lateinit var cpass : TextView
//    private lateinit var mainpwd : TextView
//    private lateinit var  nametext :TextView
//    private lateinit var  ptext : TextView

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

        addtext = findViewById(R.id.addtext)
        login = findViewById(R.id.login)

        passcon = findViewById(R.id.passcon)
//        adds = findViewById(R.id.adds)
//        cpass = findViewById(R.id.cpass)
//        mainpwd = findViewById(R.id.mainpwd)
//        ptext = findViewById(R.id.ptext)
//        nametext = findViewById(R.id.nametext)
        pwd = findViewById(R.id.pwd)
        submit.setOnClickListener {

            val confirmpassword : String = confirmpass.text.toString()
            val password : String = password.text.toString()
            validatePhone()
            validateName()
            validateAddress()
            validatePassword(password)
            validateConfirmPassword(confirmpassword)
            checkPassword(confirmpassword, password)
        }

        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }



    private  fun validatePhone(): Boolean{

        val phone : String = phonenumber.text.toString()

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
        val name : String = fullname.text.toString()
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
        val address : String = address.text.toString()
        if(address.isEmpty()){

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
    }

}