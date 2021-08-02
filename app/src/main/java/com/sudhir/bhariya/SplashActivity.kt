package com.sudhir.bhariya

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import java.util.Arrays.asList

class SplashActivity : AppCompatActivity() {
   private lateinit var  topanimation : Animation
   private lateinit var  buttonanimation : Animation
   private lateinit var logo : ImageView
   private  lateinit var  textslogan : TextView
   private lateinit var slogs : TextView

    var phonenumber = ""
    var password = ""

    companion object{
        private val LOGIN_REQUEST_CODE = 7171
    }

    private lateinit var providers : List<AuthUI.IdpConfig>
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var listener : FirebaseAuth.AuthStateListener

    override fun onStart() {
        super.onStart()
        delaySplashScreen()
    }

    private fun delaySplashScreen() {
        firebaseAuth.addAuthStateListener(listener)
    }

    override fun onStop() {
        if(firebaseAuth != null && listener != null) firebaseAuth.removeAuthStateListener(listener)
        super.onStop()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
        //hooks
        logo = findViewById(R.id.logo)
        textslogan = findViewById(R.id.textslogan)
        slogs = findViewById(R.id.slogs)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //Animations
        topanimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        buttonanimation = AnimationUtils.loadAnimation(this, R.anim.button_animation)

        logo.setAnimation(topanimation)
        textslogan.setAnimation(buttonanimation)
        slogs.setAnimation(buttonanimation)

        val secondsDelayed = 1
        if (phonenumber == "" && password == "") {

            Handler().postDelayed(Runnable {
                val options : ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity,
                    Pair.create(logo, "logo_image"))
                startActivity(Intent(this, LoginActivity::class.java),
                    options.toBundle()
                )
                    finish()
            }, (secondsDelayed * 3000).toLong())
        } else {
            Handler().postDelayed(Runnable {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }, (secondsDelayed * 2000).toLong())

        }

    }

    private fun init() {
        providers = Arrays.asList(
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        
        firebaseAuth = FirebaseAuth.getInstance()
        listener = FirebaseAuth.AuthStateListener { myFirebaseAuth ->
            val user = myFirebaseAuth.currentUser
            if(user!=null)
                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
            else
                loginShow()
        }
    }

    private fun loginShow() {
        val authMethodPickerLayout = AuthMethodPickerLayout.Builder(R.layout.activity_login)
            .setGoogleButtonId(R.id.btnGmail)
            .setPhoneButtonId(R.id.btnPhone)
            .build();

        startActivityForResult(AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAuthMethodPickerLayout(authMethodPickerLayout)
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build(), LOGIN_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LOGIN_REQUEST_CODE){
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
            }
            else
                Toast.makeText(this, ""+response!!.error!!, Toast.LENGTH_SHORT).show()
        }
    }

}