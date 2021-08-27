package com.sudhir.bhariya.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sudhir.bhariya.R
import com.sudhir.bhariya.ServiceBuilder
import com.sudhir.bhariya.entity.Feedback

class FeedshowAdapter ( val context: Context):
    RecyclerView.Adapter<FeedshowAdapter.ItemViewHolder>() {
    var lstFeedback : List<Feedback> = emptyList()
    fun setList(list: List<Feedback>) {
        lstFeedback = list
        notifyDataSetChanged()
    }
    class ItemViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val feedback : TextView
        val username : TextView

        init {
            feedback = view.findViewById(R.id.feedback)
            username = view.findViewById(R.id.username)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.posts,parent, false)
        return  ItemViewHolder(view)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val Feedback =lstFeedback[position]
        holder.feedback.text = Feedback.feedback
        holder.username.text = Feedback.username.toString()


    }
    override fun getItemCount(): Int {
        return lstFeedback.size
    }
}