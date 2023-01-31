package com.example.letsorder.data

import android.util.Log
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.model.*
import com.example.letsorder.viewmodel.MenuViewModel


class Datasource {

    //for waiter

//    fun addToTables(table: Table) {
//        if (!tables.containsKey(table.number)) {
//            tables[table.number] = table.order.dishes
//        }
//    }
//
//    fun loadOrderForTable(tableNum: Int): List<OrderDetails> {
//        return tables[tableNum]!!
//    }

    fun removeTable(){
        //TODO: remove the ready table from the firebase, so each table can be there only once
    }

    //for client
    fun addDishToLocalOrder(dish: Dish): Map<Dish, Int> {
        if (!sent){
            if (localOrder.containsKey(dish)) {
                localOrder[dish] = localOrder.getValue(dish) + 1
            } else {
                localOrder[dish] = 1
            }
        }
        return localOrder
    }

    fun removeDishFromLocalOrder(dish: Dish) : Map<Dish, Int> {
        if (!sent) {
            if (localOrder.getValue(dish) == 1){
                localOrder.remove(dish)
            } else {
                localOrder[dish] = localOrder.getValue(dish) - 1
            }
        }
            return localOrder
    }

    fun loadLocalOrder(): Map<Dish, Int> {
        return localOrder
    }

//    fun createLocalOrder() {
//        localOrder = Order(1, 1) //from the qr
//    }

    //for admin
    fun addDishToMenu(category: String, title: String, description: String, price: Double) {
        val dishesRef = database.getReference("menus/0/dishes")
        val nextId = (1..10000).random()
        //TODO: check if id exists
        val newDish = Dish(category, nextId, title, description, price)
        val newDishRef = dishesRef.push()
        newDishRef.setValue(newDish)
    }

    fun addWaiter(waiter: Waiter) {
        waiters.add(waiter)
    }

    fun deleteWaiter(waiter: Waiter): List<Waiter> {
        waiters.remove(waiter)
        return waiters
    }

    fun loadWaiters(): List<Waiter> {
        return waiters
    }

    companion object {
        private var database = FirebaseDatabaseSingleton.getInstance()
        //TODO:
        // it is going to stay here and when the client clicked "order" it is going to be add to the public order // for one person only at first
        // move to public firebase order // add listener to the waiter to see the changes and move the order to private orders
        var localOrder = mutableMapOf<Dish, Int>()
        var sent = false

        private val waiters = mutableListOf(Waiter("waiter1", "1234"))
        private var tables = mutableMapOf<Int, List<OrderDetails>>()

    }
}