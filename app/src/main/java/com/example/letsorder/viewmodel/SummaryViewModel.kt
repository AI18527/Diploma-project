package com.example.letsorder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.data.Datasource
import com.example.letsorder.model.Dish
import com.example.letsorder.model.Order
import com.example.letsorder.model.OrderDetails


class SummaryViewModel : ViewModel() {
    private var map = Datasource().loadLocalOrder()

    private var _bill = MutableLiveData<Double>()
    val bill: LiveData<Double>
        get() = _bill

    private var _order = MutableLiveData<Order>()
    val order : LiveData<Order>
        get() = _order

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("/publicOrders/")

    fun updateData(updatedMap :Map<Dish, Int>){
        map = updatedMap
        sumBill()
    }

    fun sumBill() {
        var currBill = 0.0
        for (item in map.keys){
            currBill += item.price * map[item]!!
        }
        _bill.postValue(currBill)
    }

    fun sendOrder(){
        Datasource.sent = true

        var dishes = arrayListOf<OrderDetails>()
        for (item in map) {
            dishes.add(OrderDetails(item.key.title, item.value))
        }
        val newOrder = bill.value?.let {
            Order(
                active = Datasource.sent,
                bill = it,
                dishes = dishes,
                flagForWaiter = false,
                restaurantId = 1,
                tableNum = Datasource.tableNum
            )
        }
        ref.push().setValue(newOrder)
    }

    companion object{
        var active: Boolean = false
    }
}