package com.sudhir.bhariya.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.data.model.User
import com.sudhir.bhariya.R
import com.sudhir.bhariya.entity.Feedback

class FeedbackAdapter(private val feedbackList : ArrayList<Feedback>) : RecyclerView.Adapter<FeedbackAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = feedbackList[position]

        holder.username.text = currentitem.username
        holder.feedback.text = currentitem.feedback

    }

    override fun getItemCount(): Int {

        return feedbackList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val username : TextView = itemView.findViewById(R.id.tvusername)
        val feedback : TextView = itemView.findViewById(R.id.tvfeedback)

    }

}