package com.sudhir.bhariya

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.sudhir.bhariya.Repository.DriverRepository
import com.sudhir.bhariya.Repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DriverRegistrationActivity : AppCompatActivity() {

    private var imagePicker: ImageView? = null
    private var imagePicker2 : ImageView? = null
    private var imagePicker3 : ImageView? = null
    private lateinit var btnSave : Button
    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null
    private var imageUrl2 : String? = null
    private var imageUrl3 : String? = null
    private var pointer : Int? = 0


    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
//    private var imagePicker2: ImageView? = null
//    private var imagePicker3: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_registration)
        //Using Static Phone Number for Test
        var phonenumber = "9860059091"
        imagePicker = findViewById(R.id.picker_image1)
        imagePicker2 = findViewById(R.id.picker_image2)
        imagePicker3 = findViewById(R.id.picker_image3)

        val gallery = findViewById<Button>(R.id.gallery)
        val camera = findViewById<Button>(R.id.camera)

        val gallery2 = findViewById<Button>(R.id.gallery2)
        val camera2 = findViewById<Button>(R.id.camera2)

        val gallery3 = findViewById<Button>(R.id.gallery3)
        val camera3 = findViewById<Button>(R.id.camera3)

        btnSave = findViewById(R.id.btnSave)




        gallery.setOnClickListener {
            gallery()
            pointer = 1
        }

        camera.setOnClickListener {
            camera()
            pointer = 1

        }

        gallery.setOnClickListener {
            gallery()
            pointer = 2

        }

        camera2.setOnClickListener {
            camera()
            pointer = 2

        }

        gallery3.setOnClickListener {
            gallery()
            pointer = 3
        }

        camera3.setOnClickListener {
            camera()
            pointer = 3
        }

        btnSave.setOnClickListener{
            if(validation()) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val file = File(imageUrl!!)
                        val mimeType = getMimeType(file);
                        val reqFile =
                            RequestBody.create(MediaType.parse(mimeType!!), file)
                        val body =
                            MultipartBody.Part.createFormData("image", file.name, reqFile)
                        val repository = DriverRepository().imageOneUpload(
                            phonenumber = phonenumber,
                            body
                        )
                        println("##########################")
                        println(body.toString())
                    }
                    catch(ex : Exception){
                        println(ex)
                    }

                    try {
                        val file = File(imageUrl2!!)
                        val mimeType = getMimeType(file);
                        val reqFile =
                            RequestBody.create(MediaType.parse(mimeType!!), file)
                        val body =
                            MultipartBody.Part.createFormData("image", file.name, reqFile)
                        val repository = DriverRepository().imageTwoUpload(
                            phonenumber = phonenumber,
                            body
                        )
                        println("##########################")
                        println(body.toString())
                    }
                    catch(ex : Exception){
                        println(ex)
                    }


                    try {
                        val file = File(imageUrl3!!)
                        val mimeType = getMimeType(file);
                        val reqFile =
                            RequestBody.create(MediaType.parse(mimeType!!), file)
                        val body =
                            MultipartBody.Part.createFormData("image", file.name, reqFile)
                        val repository = DriverRepository().imageThreeUpload(
                            phonenumber = phonenumber,
                            body
                        )
                        println("##########################")
                        println(body.toString())
                        val response = repository
                        if(response.success==true){
                            println("Successfully Updated")
                            finish()
                            startActivity(Intent(this@DriverRegistrationActivity,DriverVerificationActivity::class.java))

                        }
                        else{
                            println("Update Unsuccessful")
                        }
                    }
                    catch(ex : Exception){
                        println(ex)
                    }
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (pointer == 1) {
                if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                    val selectedImage = data.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val contentResolver = contentResolver
                    val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                    cursor!!.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    imageUrl = cursor.getString(columnIndex)
                    imagePicker?.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                    cursor.close()
                } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                    val imageBitmap = data.extras?.get("data") as Bitmap
                    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                    val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                    imageUrl = file!!.absolutePath
                    imagePicker?.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                }
            }
            if (pointer == 2) {
                if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                    val selectedImage = data.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val contentResolver = contentResolver
                    val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                    cursor!!.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    imageUrl2 = cursor.getString(columnIndex)
                    imagePicker2?.setImageBitmap(BitmapFactory.decodeFile(imageUrl2))
                    cursor.close()
                } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                    val imageBitmap = data.extras?.get("data") as Bitmap
                    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                    val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                    imageUrl2 = file!!.absolutePath
                    imagePicker2?.setImageBitmap(BitmapFactory.decodeFile(imageUrl2))
                }
            }
            if (pointer == 3) {
                if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                    val selectedImage = data.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val contentResolver = contentResolver
                    val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                    cursor!!.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    imageUrl3 = cursor.getString(columnIndex)
                    imagePicker3?.setImageBitmap(BitmapFactory.decodeFile(imageUrl3))
                    cursor.close()
                } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                    val imageBitmap = data.extras?.get("data") as Bitmap
                    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                    val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                    imageUrl3 = file!!.absolutePath
                    imagePicker3?.setImageBitmap(BitmapFactory.decodeFile(imageUrl3))
                }
            }

        }
    }

    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    private fun checkRunTimePermission() {
        if (!hasPermission()) {
            requestPermission()
        }
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 1)
    }
    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }
    private fun camera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    private fun validation() : Boolean{

        return true
    }


}