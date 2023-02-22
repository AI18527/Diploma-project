package com.example.letsorder.util


import com.example.letsorder.model.Dish
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class LocalOrderTest{

    @Test
    fun addDish_test(){
        val dish = Dish("Dessert", "Pancakes", "description", 10.0, 1)
        val map = LocalOrder().addDishToLocalOrder(dish)
        assertEquals(mapOf(dish to 1), map)
    }

    @Test
    fun removeDish_test(){
        val dish = Dish("Dessert", "Pancakes", "description", 10.0, 1)
        var map = mapOf(dish to 1)
        map = LocalOrder().removeDishFromLocalOrder(dish)
        assertTrue(map.isEmpty())
    }
}