package com.example.letsorder.model

data class Order(
    val restaurantId: Int = 0,
    val tableNum: Int = 0,
    var bill : Double = 0.0,
    var dishes: List<OrderDetails> = arrayListOf(),
    var flagForWaiter: Flag = Flag.NEW
)
enum class Flag {
    NEW, CALL, BILL, OK

}
