package com.example.letsorder.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.data.Datasource
import com.example.letsorder.model.Dish

class DishEditViewModel : ViewModel() {

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("menus/0/dishes")

    fun addDishToMenu(category: String, title: String, description: String, price: Double) {
        val nextId = (1..10000).random()
        //TODO: check if id exists
        val newDish = Dish(category, nextId, title, description, price)
        ref.push().setValue(newDish)
    }
}