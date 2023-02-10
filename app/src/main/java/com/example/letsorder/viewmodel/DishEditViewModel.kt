package com.example.letsorder.viewmodel

import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.model.Dish
import com.google.mlkit.vision.common.InputImage
import java.io.IOException

class DishEditViewModel : ViewModel() {

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("dishes")

    fun addDishToMenu(category: String, title: String, description: String, price: Double) {
       /* val image: InputImage
        try {
            image = InputImage.fromFilePath(context, uri)
        } catch (e: IOException) {
            e.printStackTrace()
        }*/

        val newDish = Dish(category, title, description, price, 1)
        ref.push().setValue(newDish)
    }
}