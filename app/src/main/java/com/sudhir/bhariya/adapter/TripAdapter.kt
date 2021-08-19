package com.sudhir.bhariya.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.sudhir.bhariya.R
import com.sudhir.bhariya.entity.Trip

class TripAdapter (val lstTrip: List<Trip>, val context: Context):RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    class TripViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val date: MaterialTextView
        val Source: MaterialTextView
        val Destination: MaterialTextView
        val refid : MaterialTextView
        val Price : MaterialTextView


        //        val itemComment : TextView
        init {
            date = view.findViewById(R.id.dates)
            Source = view.findViewById(R.id.source)
            Destination = view.findViewById(R.id.Destination)
            refid = view.findViewById(R.id.referenceid)
            Price = view.findViewById(R.id.price)


//            itemOrderPlace = view.findViewById(R.id.)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripAdapter.TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history, parent, false)
        return  TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripAdapter.TripViewHolder, position: Int) {
        val trip =lstTrip[position]
        holder.date.text = trip.Date.toString()
        holder.Source.text = trip.Source.toString()
        holder.Destination.text = trip.Destination.toString()
        holder.refid.text = trip.ReferenceId.toString()
        holder.Price.text = trip.Cost.toString()

    }

    override fun getItemCount(): Int {
        return lstTrip.size
    }
}