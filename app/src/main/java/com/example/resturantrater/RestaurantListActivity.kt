package com.example.resturantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.resturantrater.databinding.ActivityMainBinding
import com.example.resturantrater.databinding.ActivityRestaurantListBinding
import com.google.firebase.firestore.FirebaseFirestore

class RestaurantListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRestaurantListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //connect to db
        val db = FirebaseFirestore.getInstance().collection("Restaurants")

        val query = db.get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.i("Db_Response", "{$document.data}")
                val restaurant = document.toObject(Resaurant::class.java)

                //add restaurant object to our view to linear list
                val textView = TextView(this)
                textView.text = restaurant.name
                textView.textSize = 20f

                binding.linearLayout.addView(textView)

            }
        }
    }
}