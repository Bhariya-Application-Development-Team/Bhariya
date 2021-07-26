package com.sudhir.bhariya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.sudhir.bhariya.Repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private lateinit var editprofile: CardView
    private lateinit var driverprofile: CardView
    private lateinit var userimg: ImageView
    private lateinit var etfullname: TextView
    private lateinit var etphonenumber: TextView
    private lateinit var etaddress: TextView
    private lateinit var etgender: TextView
    private lateinit var logout: CardView
    var imagepath : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        userimg = findViewById(R.id.userimg)
        etfullname = findViewById(R.id.etfullname)
        etphonenumber = findViewById(R.id.etphonenumber)
        etaddress = findViewById(R.id.etaddress)
//        etgender = findViewById(R.id.etgender)

        driverprofile = findViewById(R.id.driverprofile)
        editprofile = findViewById(R.id.editprofile)
        logout = findViewById(R.id.logout)

        driverprofile.setOnClickListener {
            startActivity(
                Intent(
                    this@ProfileActivity,
                    DriverRegistrationActivity::class.java
                )

            )
        }
        logout.setOnClickListener {
            val sharedPreference = getSharedPreferences(
                "MyPreference",
                AppCompatActivity.MODE_PRIVATE
            )
            val editor = sharedPreference?.edit()
            if (editor != null) {
                editor.remove("phonenumber")

                editor.remove("password")
                editor.commit()
                finish()
                startActivity(
                    Intent(
                        this@ProfileActivity,
                        LoginActivity::class.java
                    )

                )
            }
        }
        editprofile.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java)
            intent.putExtra("fullname",etfullname.text.toString())
            intent.putExtra("address",etaddress.text.toString())
            intent.putExtra("phonenumber",etphonenumber.text.toString())
            intent.putExtra("imagepath",imagepath)
            startActivity(intent)

        }

        ProfileDetail();


    }

    private fun ProfileDetail() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepository()
                val response = userRepository.viewUser()
                if (response.success == true) {
                    var phonenumber = response.PhoneNumber.toString()
                    var fullname = response.Fullname.toString()
                    var address = response.Address.toString()
                    imagepath = response.image.toString()



                    var imagePath = ServiceBuilder.loadprofilePath() + response.image.toString()
                    imagePath = imagePath.replace("\\", "/")

                    withContext(Dispatchers.Main) {
                        etphonenumber.text = phonenumber;
                        etfullname.text = fullname;
                        etaddress.text = address;
                        Glide.with(this@ProfileActivity)
                            .load(imagePath)
                            .fitCenter()
                            .into(userimg)
                    }


                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Error : ${ex.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    }
