package com.sudhir.bhariya

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker

class DriverRegistrationActivity : AppCompatActivity() {

    private var imagePicker: ImageView? = null
//    private var imagePicker2: ImageView? = null
//    private var imagePicker3: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_registration)

        imagePicker = findViewById(R.id.picker_image1)
        imagePicker = findViewById(R.id.picker_image2)
        imagePicker = findViewById(R.id.picker_image3)

        val gallery = findViewById<Button>(R.id.gallery)
        val camera = findViewById<Button>(R.id.camera)


        gallery.setOnClickListener {

            ImagePicker.with(this).galleryOnly().galleryMimeTypes(arrayOf("image/*")).crop()
                .maxResultSize(400, 400).start()

        }

        camera.setOnClickListener {

            ImagePicker.with(this).cameraOnly().crop().maxResultSize(400, 400).start()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK && requestCode== ImagePicker.REQUEST_CODE) {


            imagePicker?.setImageURI(data?.data)

        }

    }


}