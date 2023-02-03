package com.example.letsorder.model

data class Table(
    val tableNum: Int = 0,
    val orders: List<Int> = arrayListOf(),
    val capacity: Int = 0,
    val restaurantId: Int = 0
)
