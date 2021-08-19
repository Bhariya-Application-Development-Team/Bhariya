package com.sudhir.bhariya


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sudhir.bhariya.Repository.FeedbackRepository
import com.sudhir.bhariya.adapter.FeedbackAdapter
import com.sudhir.bhariya.adapter.FeedshowAdapter
import com.sudhir.bhariya.db.UserDB
import com.sudhir.bhariya.entity.Feedback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class FeedbackActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : FeedshowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        recyclerView = findViewById(R.id.feedbacklist)
        recyclerView.layoutManager = LinearLayoutManager(this,)
        adapter = FeedshowAdapter(this@FeedbackActivity)
        recyclerView.adapter = adapter
        loadFeedback()
    }
    private fun loadFeedback() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val feedbackRepository = FeedbackRepository()
                val feedback = Feedback(
                    username = "",
                    feedback = ""
                )
                val response = feedbackRepository.getAllFeedback(feedback)
                if (response.success == true) {
                    Log.d("This", "Done")
                    var lstFeedback = response.feedback!!
                    // for storing in room database
                    val FeedbackDAO = UserDB.getInstance(this@FeedbackActivity).getFeedbackDAO()
                    // adding university in list
                    for (feedback in lstFeedback){
                        FeedbackDAO.addFeedback(feedback)
                    }
                    //getting value of all items and saving into room database
                    lstFeedback = FeedbackDAO.getAllFeedback()
                    Log.d("this", "this")
//                    withContext(Main){
//                    val  lstUniversity = response.university!!

                    withContext(Dispatchers.Main) {
                        adapter.setList(lstFeedback)
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {

//                    Toast.makeText(activity, "Error:${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}