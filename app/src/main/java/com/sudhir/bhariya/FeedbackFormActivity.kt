package com.sudhir.bhariya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sudhir.bhariya.Repository.FeedbackRepository
import com.sudhir.bhariya.adapter.FeedbackAdapter
import com.sudhir.bhariya.db.UserDB
import com.sudhir.bhariya.entity.Feedback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedbackFormActivity : AppCompatActivity() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var userList:ArrayList<Feedback>
    private lateinit var feedbackAdapter:FeedbackAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_form)
        /**set List*/
        userList = ArrayList()
        /**set find Id*/
        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)
        /**set Adapter*/
        feedbackAdapter = FeedbackAdapter(this,userList)
        /**setRecycler view Adapter*/
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = feedbackAdapter
        /**set Dialog*/
        addsBtn.setOnClickListener { addInfo() }

    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_item,null)
        /**set view*/
        val etfeedback = v.findViewById<EditText>(R.id.etfeedback)
        val etusername = v.findViewById<EditText>(R.id.etusername)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
            val text = etfeedback.text.toString()
            val name = etusername.text.toString()

            userList.add(Feedback("$text"," $name"))
            CoroutineScope(Dispatchers.IO).launch {
                val feedbackRepository = FeedbackRepository()
                val feedback = Feedback(

                    username = "admin",
                    feedback = text
                )
                val response = feedbackRepository.insertFeedback(feedback)
                if(response.success == true){
                    println("#############################")
                    println("Successfully Feedback Posted")
                }
            }
            feedbackAdapter.notifyDataSetChanged()
            Toast.makeText(this@FeedbackFormActivity,"Feedback successfully added.",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton(
            "Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()
    }
    /**ok now run this */
}