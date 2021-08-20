package com.sudhir.bhariya.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sudhir.bhariya.R
import com.sudhir.bhariya.Repository.TripRepository
import com.sudhir.bhariya.adapter.TripAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HistoryFragment : Fragment() {

    private lateinit var history : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val root =  inflater.inflate(R.layout.fragment_history, container, false)

        history = root.findViewById(R.id.historyrecyclerview)

        loadTrip()

        return root
    }

    private fun loadTrip(){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val itemsRepository = TripRepository()
                val response = itemsRepository.getAllTrips()
                if(response.success == true){
//                    Log.d("This", "Done")
                    var lstTrip= response.data!!
//                    // for storing in room database
//                    val ItemsDAO = CustomerDB.getInstance(requireContext()).getItemsDAO()
//                    // adding items in list
//                    for (items in lstItems){
//                        ItemsDAO.addItems(items)
//                    }
//                    //getting value of all items and saving into room database
//                    lstItems = ItemsDAO.getAllItems()
                    withContext(Dispatchers.Main){
                        history.adapter = context?.let { TripAdapter(lstTrip, it) }
                        history.layoutManager = GridLayoutManager(context, 1)



                    }



                }
            } catch(ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}