package com.example.letsorder.viewmodel

import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.model.Table

class TablesEditViewModel : ViewModel() {
    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("tables")

    fun addTable(tableNum:Int, capacity: Int) {
        val table = Table(tableNum = tableNum, capacity = capacity, restaurantId = 1)
        ref.push().setValue(table)
    }
}