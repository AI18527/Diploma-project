package com.example.letsorder.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.letsorder.util.FirebaseDatabaseSingleton
import com.example.letsorder.util.RestaurantInfo
import com.example.letsorder.model.Dish
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class NewDishViewModel : ViewModel() {
    val storage = Firebase.storage
    var storageRef = storage.reference

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("dishes")

    fun addDishWithPick(title: String,category: String, description: String, price: Double, image: Uri?){

        var newDish : Dish
        if (image != null) {
            val path = "images/" + UUID.randomUUID().toString()
            val newImagesRef = storageRef.child(path)
            val result = newImagesRef.putFile(image)

            result.addOnFailureListener {
                Log.d("Error", "Could not upload the image")
            }.addOnSuccessListener {
                Log.d("TAG", "Success")
            }

            newDish = Dish(category, title, description, price, RestaurantInfo.restaurantId, path)
        }
        else {
            newDish = Dish(category, title, description, price, RestaurantInfo.restaurantId)
        }
        ref.push().setValue(newDish)
    }
}