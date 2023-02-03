package com.example.letsorder.data

import com.example.letsorder.model.Dish

class LocalOrder {
    fun addDishToLocalOrder(dish: Dish): Map<Dish, Int> {
        if (!Datasource.sent){
            if (localOrder.containsKey(dish)) {
                localOrder[dish] = localOrder.getValue(dish) + 1
            } else {
                localOrder[dish] = 1
            }
        }
        return localOrder
    }

    fun removeDishFromLocalOrder(dish: Dish) : Map<Dish, Int> {
        if (!Datasource.sent) {
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
        private var localOrder = mutableMapOf<Dish, Int>()
    }
}