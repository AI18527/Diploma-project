package com.example.letsorder.data

import android.util.ArraySet
import android.util.Log
import androidx.collection.arraySetOf
import com.example.letsorder.model.Dish
import com.example.letsorder.model.Table
import com.example.letsorder.model.Waiter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Datasource {
    private lateinit var database: DatabaseReference

    fun loadWaiters(): List<Waiter>{
        return waiters
    }

    fun loadDishes(): List<Dish> {
        return menu
    }

    fun loadOrder(): List<Dish> {
        return order
    }

    fun loadTables() : List<Table> {
        return tables
    }

    fun loadOrderforTable(tableNum:Int): List<Dish>{
        return tables.find { table -> table.number == tableNum }!!.order

    }

    fun addOrderToTable(tableNum: Int, order: List<Dish>) {
        tables.add(Table(tableNum, order, true))
    }

    fun addDishToOrder(dish: Dish) {
        order.add(dish)
    }

    fun addDishToMenu(category: String, title:String, description: String, price: Double){
        val dish = hashMapOf(
            "dishName" to title,
            "description" to description,
            "category" to category,
            "price" to price,
            "id" to id++

        )

        database = Firebase.database.reference

        menu.add(Dish(category, id, title, description, price))
        //val dish = Dish(category, 2, title, description, price)
        val dataRef = database.child("menus")//.child("dishes")
        //val key = dataRef.push().key
        dataRef.child(dataRef.push().key!!).setValue(dish)

    }

    fun addWaiter(waiter: Waiter) {
        waiters.add(waiter)
    }

    fun deleteWaiter(waiter:Waiter): List<Waiter>{
        waiters.remove(waiter)
        return waiters
    }

    companion object {
        private val menu = ArrayList<Dish>()
        private val order = ArrayList<Dish>()
        private val waiters = mutableListOf(Waiter("waiter1", "1234"))
        private val tables = mutableListOf(Table(1, listOf(Dish("Soups", 1, "Tomato soup", "Creamy tomato soup", 7.60)), true))

        private var id: Int = 0
    }
}