package com.sudhir.bhariya.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.sudhir.bhariya.R
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener


class DashboardFragment : Fragment() {
    var sampleImages = intArrayOf(
        R.drawable.logo,
        R.drawable.logo,

        )
    private lateinit var myDialog : Dialog
    private lateinit var miniButton  : LinearLayout
    private lateinit var mediumButton  : LinearLayout
    private lateinit var  typeVehicle : TextView
    private lateinit var vehicleImage : ImageView
    private lateinit var  heavyButton : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val carouselView = root.findViewById(R.id.carouselviews) as CarouselView;

            val pop = inflater.inflate(R.layout.custompopup, container, false)
            carouselView.setImageListener(imageListener);
        carouselView.setPageCount(sampleImages.size);
        miniButton = root.findViewById(R.id.btnMini)
        mediumButton = root.findViewById(R.id.mediumButton)
        heavyButton = root.findViewById(R.id.heavyButton)
        myDialog = context?.let { Dialog(it) }!!

            miniButton.setOnClickListener{
                getAttr(pop)
                typeVehicle.setText("Mini Truck")
                vehicleImage.setImageResource(R.drawable.truck1)
                showPopup(pop)
            }

            mediumButton.setOnClickListener{
            getAttr(pop)
            typeVehicle.setText("Medium Truck")
            vehicleImage.setImageResource(R.drawable.truck3)
            showPopup(pop)



        }

        heavyButton.setOnClickListener {
            getAttr(pop)
            typeVehicle.setText("Heavy Truck")
            vehicleImage.setImageResource(R.drawable.truck2)
            showPopup(pop)
        }


        return root

    }
    private fun getAttr(view : View){
        typeVehicle = view.findViewById(R.id.typeVehicle)
        vehicleImage = view.findViewById(R.id.vehicleImage)
    }

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here

            imageView.setImageResource(sampleImages[position])

        }
    }


    private fun showPopup(view: View){
            myDialog.setContentView(view)
        myDialog.show()
    }

}