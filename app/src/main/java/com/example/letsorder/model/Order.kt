package com.example.letsorder.model

data class Order(
    val table: Int = 0,
    var active: Boolean = false,
    var dishes: MutableMap<Dish, Int> = mutableMapOf(),
    var flagForWaiter: Boolean = false,
)
