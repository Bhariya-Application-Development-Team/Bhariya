package com.sudhir.bhariya

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sudhir.bhariya.Repository.UserRepository
import com.sudhir.bhariya.fragments.DashboardFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    lateinit var ref: DatabaseReference
    private lateinit var topanimation: Animation
    private lateinit var buttonanimation: Animation
    private lateinit var logo: ImageView
    private lateinit var textslogan: TextView
    private lateinit var slogs: TextView
    var phonenumber : String? = null
    var password : String? = null
    private lateinit var webview: WebView
    private lateinit var triagain: Button
    private lateinit var myDialog: Dialog
    var pointer: Boolean? = null

    @SuppressLint("ServiceCast")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //hooks
        ref = FirebaseDatabase.getInstance().getReference()
        get()
        logo = findViewById(R.id.logo)
        textslogan = findViewById(R.id.textslogan)
        slogs = findViewById(R.id.slogs)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //Animations
        topanimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        buttonanimation = AnimationUtils.loadAnimation(this, R.anim.button_animation)

        logo.setAnimation(topanimation)
        textslogan.setAnimation(buttonanimation)
        slogs.setAnimation(buttonanimation)


        webview = findViewById(R.id.webview)

        val webSetting = webview.settings
        webSetting.javaScriptEnabled = true

        //Initialized Connectivity Manager
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        //Get network info
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        if ((activeNetwork == null || !activeNetwork.isConnected()) || (activeNetwork == null || !activeNetwork.isAvailable())) {
            // Check Network Status
//        if(activeNetwork == null ||  activeNetwork.isAvailable()){
            //when Internet is inactive

            //Initialize dialog
            myDialog = Dialog(this@SplashActivity)
            myDialog.setContentView(R.layout.nointernet)
            triagain = myDialog.findViewById(R.id.tryagain)
            myDialog.setCanceledOnTouchOutside(false);
            //set dialog width and height
            myDialog.window?.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            //set animation
            myDialog.window?.attributes?.windowAnimations = android.R.style.Animation_Dialog

            //Initialize dialog Variable


            //perform click operation

            triagain.setOnClickListener {
                recreate()
            }

            myDialog.show()


        } else {

            if (pointer == true) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                val secondsDelayed = 1
                if (phonenumber == null && password == null) {

                    Handler().postDelayed(Runnable {
                        val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                            this@SplashActivity,
                            Pair.create(logo, "logo_image")
                        )
                        startActivity(
                            Intent(this, LoginActivity::class.java),
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
    }

    fun get() {
        var sharedPref: SharedPreferences = getSharedPreferences("MyPreference", MODE_PRIVATE)

        phonenumber = sharedPref.getString("phonenumber", "").toString()
        password = sharedPref.getString("password", "").toString()
        var token = sharedPref.getString("token", "").toString()
        if (phonenumber != "" && password!="") {
            pointer = true
            try {
                CoroutineScope(Dispatchers.IO).launch() {
                    var repository = UserRepository()
                    var respose = repository.checkUser(phonenumber!!, password!!)
                    respose.token
                    println("############")
                    println(respose.token)
                    ServiceBuilder.token = "Bearer ${respose.token}"
                    ref.child("drivers/9852051425/longitude").setValue(respose.token.toString());
                }
            }catch (error : Error){
                println(error.toString())
            }

        }else
            pointer = false


    }
}