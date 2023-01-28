package com.example.letsorder.model

data class Order(
    val restaurantId: Int = 0,
    val tableNum: Int = 0,
    var bill : Double = 0.0,
    var active: Boolean = false,
    var dishes: List<OrderDetails> = arrayListOf<OrderDetails>(),
    var flagForWaiter: Boolean = false
)