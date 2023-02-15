package com.example.letsorder.model

data class Dish(
    val category: String = "category",
    val title: String = "title",
    val description: String = "description",
    val price: Double = 0.0,
    val restaurantId: Int = 0,
    val image: String? = null
)


