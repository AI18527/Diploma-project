package com.example.letsorder.viewmodel

import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.model.Dish

class DishEditViewModel : ViewModel() {

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("dishes")

    fun addDishToMenu(category: String, title: String, description: String, price: Double) {
        val newDish = Dish(category, title, description, price, 1)
        ref.push().setValue(newDish)
    }
}