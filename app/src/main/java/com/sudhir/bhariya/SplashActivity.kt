package com.sudhir.bhariya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashActivity : AppCompatActivity() {
   private lateinit var  topanimation : Animation
   private lateinit var  buttonanimation : Animation
   private lateinit var logo : ImageView
   private  lateinit var  textslogan : TextView
   private lateinit var slogs : TextView
    var phonenumber = ""
    var password = ""
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
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }, (secondsDelayed * 2000).toLong())
        } else {
            Handler().postDelayed(Runnable {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }, (secondsDelayed * 2000).toLong())

        }

    }
}