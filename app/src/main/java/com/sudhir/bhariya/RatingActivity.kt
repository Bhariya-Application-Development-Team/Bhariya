package com.sudhir.bhariya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast

class RatingActivity : AppCompatActivity() {
    private lateinit var rb_ratingBar: RatingBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        rb_ratingBar = findViewById(R.id.rb_ratingBar)



        rb_ratingBar.rating = 2.5f
        rb_ratingBar.stepSize = .5f

        rb_ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUSer ->
            Toast.makeText(this, "Rating: $rating", Toast.LENGTH_SHORT).show()
        }

    }
}