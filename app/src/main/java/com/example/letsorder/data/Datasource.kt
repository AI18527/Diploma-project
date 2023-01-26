package com.example.letsorder.data

import android.util.ArraySet
import android.util.Log
import androidx.collection.arraySetOf
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.model.Dish
import com.example.letsorder.model.Table
import com.example.letsorder.model.Waiter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Math.random

class Datasource {

    fun loadWaiters(): List<Waiter>{
        return waiters
    }

    fun loadDishes(): List<Dish> {
        val ref = database.getReference("menus/0/dishes")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val value = snapshot.getValue(Dish::class.java)
                    if (value != null) {
                        if (!menu.contains(value)) {
                            menu.add(value)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("Data", "loadPost:onCancelled", error.toException())
            }
        })
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
        val dishesRef = database.getReference("menus/0/dishes")
        val newDish = Dish(category, id, title,  description,price)
        val newDishRef = dishesRef.push()
        newDishRef.setValue(newDish)

    }

    fun addWaiter(waiter: Waiter) {
        waiters.add(waiter)
    }

    fun deleteWaiter(waiter:Waiter): List<Waiter>{
        waiters.remove(waiter)
        return waiters
    }

    companion object {
        private var database = FirebaseDatabaseSingleton.getInstance()
        private val menu = ArrayList<Dish>()
        private val order = ArrayList<Dish>()
        private val waiters = mutableListOf(Waiter("waiter1", "1234"))
        private val tables = mutableListOf(Table(1, listOf(Dish("Soups", 1, "Tomato soup", "Creamy tomato soup", 7.60)), true))

        private var id: Int = 0
    }
}