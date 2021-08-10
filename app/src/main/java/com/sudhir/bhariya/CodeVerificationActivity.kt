package com.sudhir.bhariya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.safetynet.SafetyNet
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class CodeVerificationActivity : AppCompatActivity() {
    private lateinit var tvNocode : TextView
    private lateinit var btnContinue : Button
    private lateinit var etCode : EditText
    var code = ""
    var phonenumber = ""
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

//        tvNocode = findViewById(R.id.tvNocode)

        FirebaseApp.initializeApp(this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance())

        SafetyNet.getClient(this)
            .enableVerifyApps()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result?.isVerifyAppsEnabled == true) {
                        Log.d("MY_APP_TAG", "The user gave consent to enable the Verify Apps feature.")
                    } else {
                        Log.d(
                            "MY_APP_TAG",
                            "The user didn't give consent to enable the Verify Apps feature."
                        )
                    }
                } else {
                    Log.e("MY_APP_TAG", "A general error occurred.")
                }
            }

        SafetyNet.getClient(this)
            .isVerifyAppsEnabled
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result?.isVerifyAppsEnabled == true) {
                        println("Reached Here")
                        Log.d("MY_APP_TAG", "The Verify Apps feature is enabled.")
                    } else {
                        Log.d("MY_APP_TAG", "The Verify Apps feature is disabled.")
                    }
                } else {
                    Log.e("MY_APP_TAG", "A general error occurred.")
                }
            }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.

                code = credential.smsCode.toString()
                print(credential)

                if(code != null){
                    etCode.setText(code)
                }

                val intent = Intent(this@CodeVerificationActivity, ChangePasswordActivity::class.java)
                intent.putExtra("phonenumber", phonenumber)
                startActivity(intent)

                println("#############################")
                println("#############################")
                println("#############################")


                Log.d(TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }


            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }


                println("#############################")
                println("############### ERROR ##############")
                println("#############################")
                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                println(token)
                println("##########################")
                println("##########################")
                println("######## This is the code ########")

                var phoneNumber: String = ""

            }

        }
        setContentView(R.layout.activity_code_verification)
        etCode = findViewById(R.id.etCode)
        btnContinue = findViewById(R.id.btnContinue)


        val intent = intent
        phonenumber = intent.getStringExtra("phonenumber").toString()
        if (phonenumber != null && etCode.text.toString()!="") {
            sendVerificationCodeToUser(phonenumber)
        }


//        tvNocode.setOnClickListener {
//            if (phonenumber != null) {
//                sendVerificationCodeToUser(phonenumber)
//            }
//        }

        btnContinue.setOnClickListener {
            callbacks

            if(etCode.text.toString() != "" && etCode.text.toString() == code) {
                Toast.makeText(this, "CODE VERIFIED", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,ChangePasswordActivity::class.java)
                intent.putExtra("phonenumber",phonenumber)
                startActivity(intent)
            }
            else
                Toast.makeText(this, "CODE UNVERIFIED", Toast.LENGTH_SHORT).show()
        }
    }


    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]
    }

    private fun sendVerificationCodeToUser(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+977" + phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser? = auth.currentUser) {

    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    companion object {
        private const val TAG = "PhoneAuthActivity"
    }


}