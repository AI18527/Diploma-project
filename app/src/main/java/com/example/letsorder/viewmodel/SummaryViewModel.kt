package com.example.letsorder.viewmodel

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.data.Datasource
import com.example.letsorder.model.Dish
import com.example.letsorder.model.OrderDetails


class SummaryViewModel : ViewModel() {
    private var map = Datasource().loadLocalOrder()

    private var _bill = MutableLiveData<Double>()
    val bill: LiveData<Double>
        get() = _bill

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

        val ref = FirebaseDatabaseSingleton.getInstance().getReference("/publicOrders/")

        var dishes = arrayListOf<OrderDetails>()
        for (item in map){
            dishes.add(OrderDetails(item.key.title, item.value))
        }
        val newOrder = hashMapOf(
            "active" to Datasource.sent,
            "bill" to bill.value,
            "dishes" to dishes,
            "flagForWaiter" to false,
            "restaurantId" to 1,
            "tableNum" to 1
        )
        ref.push().setValue(newOrder)

    }

}