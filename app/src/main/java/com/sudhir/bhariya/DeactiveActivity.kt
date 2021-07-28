package com.sudhir.bhariya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeactiveActivity : AppCompatActivity() {
    private lateinit var etphonenumber: EditText
    private lateinit var btndeactive: Button
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deactive)

        etphonenumber = findViewById(R.id.etphonenumber)
        btndeactive = findViewById(R.id.btndeactive)

        btndeactive.setOnClickListener{

            var username = etphonenumber.text.toString()
            if(username.isNotEmpty())
                deleteData(username)
            else
                Toast.makeText(this, "Please enter the username", Toast.LENGTH_SHORT).show()

        }
    }
    private  fun deleteData(username: String){
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(username).removeValue().addOnSuccessListener{
            etphonenumber.text.clear()
            Toast.makeText(this, "Sucessfuly Deleted",Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }
    }
}