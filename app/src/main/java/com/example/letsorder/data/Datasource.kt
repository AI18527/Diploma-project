package com.example.letsorder.data

import android.util.Log
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.model.*
import com.example.letsorder.viewmodel.MenuViewModel
import com.example.letsorder.viewmodel.QRViewModel


class Datasource {

    //for waiter

//    fun addToTables(table: Table) {
//        if (!tables.containsKey(table.number)) {
//            tables[table.number] = table.order.dishes
//        }
//    }

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

    companion object {
        //TODO:
        // add waiter to move the order to private orders
        private var localOrder = mutableMapOf<Dish, Int>()
        var sent = false
        var tableNum = 0
        var free = true
        var currOrder = Order()

//        private val waiters = mutableListOf<Waiter>()

    }
}