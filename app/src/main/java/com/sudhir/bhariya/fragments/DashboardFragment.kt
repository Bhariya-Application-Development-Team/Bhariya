package com.sudhir.bhariya.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.sudhir.bhariya.R
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import java.util.*

class DashboardFragment : Fragment() {
    var sampleImages = intArrayOf(
        R.drawable.logo,
        R.drawable.logo,

        )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val carouselView = root.findViewById(R.id.carouselviews) as CarouselView;


            carouselView.setImageListener(imageListener);
        carouselView.setPageCount(sampleImages.size);



        return root

    }
    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here

            imageView.setImageResource(sampleImages[position])

        }
    }

}