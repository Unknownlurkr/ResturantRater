package com.example.resturantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.resturantrater.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_main)

        binding.button.setOnClickListener{
            if(binding.restaurantEditText.text.toString().isNotEmpty() && binding.spinner.selectedItemPosition > 0){
                //create instance of restaurant
                val restaurant = Resaurant()
                restaurant.name = binding.restaurantEditText.toString()
                 restaurant.rating = binding.spinner.selectedItem.toString().toInt()

                //store the restaurant in firebase-firestore
                //1) get ID from firebase
                val db = FirebaseFirestore.getInstance().collection("restaurants")
                restaurant.id=db.document().id

                //sdtore restarant as a document- !! not null
                db.document(restaurant.id!!).set(restaurant)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Restaurant Added", Toast.LENGTH_LONG).show()
                        binding.restaurantEditText.setText("")
                        binding.spinner.setSelection(0)
                    }.addOnFailureListener{
                        Toast.makeText(this,"DB Error", Toast.LENGTH_LONG).show()
                        Log.i("DB Message", it.localizedMessage)
                    }

            }
            else{
                Toast.makeText(this, "resturant name and rating required", Toast.LENGTH_LONG).show()
            }
        }
    }
}