package com.sudhir.bhariya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.sudhir.bhariya.db.UserDB
import com.sudhir.bhariya.entity.Feedback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedbackFormActivity : AppCompatActivity() {

    private lateinit var  etfeedback : EditText
    private lateinit var  etusername : EditText
    private lateinit var cancel : Button
    private lateinit var send : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_form)

        etfeedback = findViewById(R.id.etfeedback)
        etusername = findViewById(R.id.etusername)
        cancel = findViewById(R.id.btnCancel)
        send = findViewById(R.id.btnSend)

        send.setOnClickListener {
            val etfeedback = etfeedback.text.toString()
            val etusername = etusername.text.toString()

            val feedback = Feedback(etfeedback, etusername)

                CoroutineScope(Dispatchers.IO).launch {
                    UserDB
                        .getInstance(this@FeedbackFormActivity)
                        .getFeedbackDAO()
                        .userFeedback(feedback)
                }
                Toast.makeText(this, "Feedback sent", Toast.LENGTH_SHORT).show()
            }
            startActivity(
                Intent(
                    this@FeedbackFormActivity,
                    FeedbackActivity::class.java)
            )
        }
    }




}