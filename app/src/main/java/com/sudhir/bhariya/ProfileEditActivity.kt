package com.sudhir.bhariya

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.textservice.TextInfo
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.sudhir.bhariya.Repository.UserRepository
import com.sudhir.bhariya.fragments.SettingFragment
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.w3c.dom.Text
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ProfileEditActivity : AppCompatActivity() {
    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    private lateinit var userImage : CircleImageView
    private lateinit var etfullname : EditText
    private lateinit var etphonenumber : EditText
    private lateinit var etaddress : EditText
    private lateinit var deactive : CardView
    private lateinit var btnSave : Button
    var primary_phone : String = ""
    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null
    var imagepath : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        //deactive account intent
        deactive = findViewById(R.id.deactive)

        deactive.setOnClickListener {

            startActivity(
                Intent(
                    this@ProfileEditActivity,
                    DeactiveActivity::class.java
                )

            )
        }

        val intent = intent

        retrieveValue()
        etfullname.setText(intent.getStringExtra("fullname"))
        etaddress.setText(intent.getStringExtra("address"))
        etphonenumber.setText(intent.getStringExtra("phonenumber"))
        primary_phone = intent.getStringExtra("phonenumber").toString()
        imagepath = intent.getStringExtra("imagepath").toString()

        var imagePath = ServiceBuilder.loadprofilePath() + imagepath
        imagePath = imagePath.replace("\\", "/")

            Glide.with(this@ProfileEditActivity)
                .load(imagePath)
                .fitCenter()
                .into(userImage)


        userImage.setOnClickListener{
            checkRunTimePermission()
            uploadOption()
        }

        btnSave.setOnClickListener{
            if(validation()) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        if (imageUrl != null) {
                            val file = File(imageUrl!!)
                            val mimeType = getMimeType(file);
                            val reqFile =
                                RequestBody.create(MediaType.parse(mimeType!!), file)
                            val body =
                                MultipartBody.Part.createFormData("image", file.name, reqFile)
                            val repository = UserRepository().updateUser(
                                primary_phone,
                                etfullname.text.toString(),
                                etaddress.text.toString(),
                                etphonenumber.text.toString(),
                                body
                            )
                            println("##########################")
                            println(body.toString())
                            startActivity(
                                Intent(
                                    this@ProfileEditActivity,
                                    SettingFragment::class.java
                                )
                            )
                            finish()
                            val response = repository
                            if (response.success == true) {
                                println("Successfully Updated")



                            } else {
                                val intent = Intent(this@ProfileEditActivity, SettingFragment::class.java)
                                finish()
                                startActivity(intent)
                            }
                        }
                        else {
                            println("######HELLO####################")

                            val repository = UserRepository().updateUserText(
                                primary_phone,
                                etfullname.text.toString(),
                                etaddress.text.toString(),
                                etphonenumber.text.toString(),
                            )
                            println("##########################")
                            startActivity(
                                Intent(
                                    this@ProfileEditActivity,
                                    SettingFragment::class.java
                                )
                            )
                            finish()
                            val response = repository
                            if (response.success == true) {
                                println("Successfully Updated")
                                startActivity(
                                    Intent(
                                        this@ProfileEditActivity,
                                        SettingFragment::class.java
                                    )
                                )
                                finish()

                            } else {
                                println("Update Unsuccessful")
                            }

                        }

                    }
                        catch(ex : Exception) {
                            println(ex)
                        }
                    }

                }
            }
        }

    private fun retrieveValue(){
        userImage = findViewById(R.id.userimg)
        etfullname = findViewById(R.id.etfullname)
        etphonenumber = findViewById(R.id.etphonenumber)
        etaddress = findViewById(R.id.etaddress)
        btnSave = findViewById(R.id.btnSave)
    }

    private fun validation() : Boolean{
        val symbols = "0123456789/?!:;%"
        val numbers = "0123456789"

        if(TextUtils.isEmpty(etfullname.text)){
            etfullname.isFocusable
            etfullname.setError("Please Enter the Full Name")
            return false

        }
        if (etfullname!!.text.any {it in symbols}) {
            etfullname.setError("Invalid Input Detected!")
            return false

        }

        if(TextUtils.isEmpty(etaddress.text)){
            etaddress.isFocusable
            etaddress.setError("Please Enter the Address")
            return false

        }
        if (etaddress!!.text.any {it in symbols}) {
            etaddress.setError("Invalid Symbol Detected!")
            return false

        }

        if(TextUtils.isEmpty(etphonenumber.text)){
            etphonenumber.isFocusable
            etphonenumber.setError("Please Enter the Phone Number")
            return false
        }
        return true
    }

    private fun uploadOption(){
        val popupMenu = PopupMenu(this@ProfileEditActivity, userImage)
        popupMenu.menuInflater.inflate(R.menu.image_upload_layout, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.uploadCamera ->
                    camera()

                R.id.uploadGallery ->
                    gallery()
            }
            true
        }
        popupMenu.show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                userImage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                userImage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
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
        ActivityCompat.requestPermissions(this@ProfileEditActivity, permissions, 1)
    }


}