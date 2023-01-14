package com.example.letsorder.data

import com.example.letsorder.model.Dish

class Datasource {

    fun loadDishes(): List<Dish> {
        //TODO: return dishesList instead
        return listOf<Dish>(
            Dish(1, "Green Salad", "It is green", 12.00),
            Dish(2, "Pizza", "Hot pizza", 15.00)
        )
    }

    fun addDishToOrder(dish: Dish) {
        order.add(dish)
    }

    fun loadOrder(): List<Dish> {
        return order
    }

    companion object {
        private val dishesList = ArrayList<Dish>()
        private val order = ArrayList<Dish>()
    }


}