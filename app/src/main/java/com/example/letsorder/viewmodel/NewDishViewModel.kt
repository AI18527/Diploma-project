package com.example.letsorder.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.letsorder.data.FirebaseDatabaseSingleton
import com.example.letsorder.data.RestaurantInfo
import com.example.letsorder.model.Dish
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class NewDishViewModel : ViewModel() {
    val storage = Firebase.storage
    var storageRef = storage.reference

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("dishes")

    fun addDishToMenu(category: String, title: String, description: String, price: Double) {
        val newDish = Dish(category, title, description, price, RestaurantInfo.restaurantId)
        ref.push().setValue(newDish)
    }

    fun addDishWithPick(title: String,category: String, description: String, price: Double, image: Uri){

        val path = "images/" + UUID.randomUUID().toString()
        val newImagesRef = storageRef.child(path)
        val result = newImagesRef.putFile(image)

        result.addOnFailureListener {
            Log.d("Error", "Could not upload the image")
        }.addOnSuccessListener {
            Log.d("TAG", "Success")
        }


        val newDish = Dish(category, title, description, price, RestaurantInfo.restaurantId,path)
        ref.push().setValue(newDish)
    }
}