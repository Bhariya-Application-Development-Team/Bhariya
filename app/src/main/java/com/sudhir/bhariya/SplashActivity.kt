package com.sudhir.bhariya

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
import androidx.annotation.RequiresApi

class SplashActivity : AppCompatActivity() {
   private lateinit var  topanimation : Animation
   private lateinit var  buttonanimation : Animation
   private lateinit var logo : ImageView
   private  lateinit var  textslogan : TextView
   private lateinit var slogs : TextView
    var phonenumber = ""
    var password = ""
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
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
}