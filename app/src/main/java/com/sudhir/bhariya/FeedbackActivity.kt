package com.sudhir.bhariya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.data.model.User
import com.google.firebase.database.*
import com.sudhir.bhariya.adapter.FeedbackAdapter
import com.sudhir.bhariya.entity.Feedback

class FeedbackActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var feedbackRecyclerview : RecyclerView
    private lateinit var feedbackArrayList : ArrayList<Feedback>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        feedbackRecyclerview = findViewById(R.id.feedbackList)
        feedbackRecyclerview.layoutManager = LinearLayoutManager(this)
        feedbackRecyclerview.setHasFixedSize(true)

        feedbackArrayList = arrayListOf<Feedback>()
        getUserData()

    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Feedback")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val feedback = userSnapshot.getValue(Feedback::class.java)
                        feedbackArrayList.add(feedback!!)

                    }

                    feedbackRecyclerview.adapter = FeedbackAdapter(feedbackArrayList)

                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}