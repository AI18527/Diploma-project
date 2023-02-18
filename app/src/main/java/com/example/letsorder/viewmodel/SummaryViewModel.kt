package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.util.FirebaseDatabaseSingleton
import com.example.letsorder.util.LocalOrder
import com.example.letsorder.model.Dish
import com.example.letsorder.model.Flag
import com.example.letsorder.model.Order
import com.example.letsorder.model.OrderDetails
import com.example.letsorder.util.RestaurantInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class SummaryViewModel : ViewModel() {
    private var map = LocalOrder().loadLocalOrder()

    private var _bill = MutableLiveData<Double>()
    val bill: LiveData<Double>
        get() = _bill

    private var _order = MutableLiveData<Order>()
    val order: LiveData<Order>
        get() = _order

    private var _sent = MutableLiveData<Boolean>()
    val sent: LiveData<Boolean>
        get() = _sent

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("/publicOrders/")
    private lateinit var listener : ValueEventListener

    fun updateData(updatedMap: Map<Dish, Int>) {
        map = updatedMap
        sumBill()
    }

    fun sumBill() {
        var currBill = 0.0
        for (item in map.keys) {
            currBill += item.price * map[item]!!
        }
        _bill.postValue(currBill)
    }

    fun sendOrder() {
        var dishes = arrayListOf<OrderDetails>()
        for (item in map) {
            dishes.add(OrderDetails(item.key.title, item.value))
        }

        val newOrder = bill.value?.let {
            Order(
                bill = it,
                dishes = dishes,
                flagForWaiter = Flag.ACCEPT,
                restaurantId = RestaurantInfo.restaurantId,
                tableNum = TableStatusViewModel().tableNum
            )
        }
        ref.push().setValue(newOrder)
        _sent.postValue(true)
    }

    fun callWaiter(flag: Flag) {
        val query = ref.orderByChild("tableNum").equalTo(TableStatusViewModel().tableNum.toDouble())
        listener = query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    if (snapshot.child("restaurantId").value.toString() == RestaurantInfo.restaurantId.toString()) {
                        snapshot.child("/flagForWaiter/").ref.setValue(flag)
                        query.removeEventListener(this)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "load:onCancelled", error.toException())
            }
        })
    }

    fun addListener(){
        ref.addValueEventListener(listener)
    }

    fun removeListener(){
        if (this::listener.isInitialized) {
            ref.removeEventListener(listener)
        }
    }
}