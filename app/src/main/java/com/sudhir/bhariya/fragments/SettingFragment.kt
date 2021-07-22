package com.sudhir.bhariya.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.sudhir.bhariya.*
import com.sudhir.bhariya.Repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SettingFragment : Fragment() {

    private lateinit var editprofile: CardView
    private lateinit var driverprofile: CardView
    private lateinit var userimg: ImageView
    private lateinit var etfullname: TextView
    private lateinit var etphonenumber: TextView
    private lateinit var etaddress: TextView
    private lateinit var etgender: TextView
    private lateinit var logout: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        userimg = view.findViewById(R.id.userimg)
        etfullname = view.findViewById(R.id.etfullname)
        etphonenumber = view.findViewById(R.id.etphonenumber)
        etaddress = view.findViewById(R.id.etaddress)
        driverprofile = view.findViewById(R.id.driverprofile)
        editprofile = view.findViewById(R.id.editprofile)
        logout = view.findViewById(R.id.logout)
        // Inflate the layout for this fragment
        driverprofile.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    ProfileActivity::class.java
                )

            )
        }

        logout.setOnClickListener {
            val sharedPreference = activity?.getSharedPreferences(
                "MyPreference",
                AppCompatActivity.MODE_PRIVATE
            )
            val editor = sharedPreference?.edit()
            if (editor != null) {
                editor.remove("phonenumber")

                editor.remove("password")
                editor.commit()
                activity?.finish()
                startActivity(
                    Intent(
                        context,
                        LoginActivity::class.java
                    )

                )
            }
        }


        editprofile.setOnClickListener {
            val intent = Intent(context, ProfileEditActivity::class.java)
            intent.putExtra("fullname", etfullname.text.toString())
            intent.putExtra("address", etaddress.text.toString())
            intent.putExtra("phonenumber", etphonenumber.text.toString())
            startActivity(intent)

        }

        ProfileDetail();


   return view
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


                    var imagePath = ServiceBuilder.loadprofilePath() + response.image.toString()
                    imagePath = imagePath.replace("\\", "/")

                    withContext(Dispatchers.Main) {
                        etphonenumber.text = phonenumber;
                        etfullname.text = fullname;
                        etaddress.text = address;
                        context?.let {
                            Glide.with(it)
                                .load(imagePath)
                                .fitCenter()
                                .into(userimg)
                        }
                    }


                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Error : ${ex.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
}

