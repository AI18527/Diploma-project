package com.example.letsorder.model

data class Dish(
    val category: String = "category",
    var id: Int = 0,
    val title: String = "title",
    val description: String = "description",
    val price: Double = 0.0
)


