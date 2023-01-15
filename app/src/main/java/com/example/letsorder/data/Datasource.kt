package com.example.letsorder.data

import com.example.letsorder.model.Dish
import com.example.letsorder.model.Waiter

class Datasource {

    fun loadWaiters(): List<Waiter>{
        return waiters
    }

    fun loadDishes(): List<Dish> {
        //TODO: return dishesList instead
        return listOf<Dish>(
            Dish(1, "Green Salad", "It is green", 12.00),
            Dish(2, "Pizza", "Hot pizza", 15.00)
        )
    }

    fun loadOrder(): List<Dish> {
        return order
    }

    fun addDishToOrder(dish: Dish) {
        order.add(dish)
    }

    fun addWaiter(waiter: Waiter) {
        waiters.add(waiter)
    }

    fun deleteWaiter(waiter:Waiter){
        waiters.remove(waiter)
    }

    companion object {
        private val dishesList = ArrayList<Dish>()
        private val order = ArrayList<Dish>()
        private val waiters = ArrayList<Waiter>()
    }


}