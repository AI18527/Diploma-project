package com.example.letsorder.util


import com.example.letsorder.model.Dish

class LocalOrder {
    fun addDishToLocalOrder(dish: Dish): Map<Dish, Int> {
        if (LOCAL_ORDER.containsKey(dish)) {
            LOCAL_ORDER[dish] = LOCAL_ORDER.getValue(dish) + 1
        } else {
            LOCAL_ORDER[dish] = 1
        }
        return LOCAL_ORDER
    }

    fun removeDishFromLocalOrder(dish: Dish): Map<Dish, Int> {
        if (LOCAL_ORDER.getValue(dish) == 1) {
            LOCAL_ORDER.remove(dish)
        } else {
            LOCAL_ORDER[dish] = LOCAL_ORDER.getValue(dish) - 1
        }
        return LOCAL_ORDER
    }

    fun loadLocalOrder(): Map<Dish, Int> {
        return LOCAL_ORDER
    }

    companion object {
        private var LOCAL_ORDER = mutableMapOf<Dish, Int>()

    }
}