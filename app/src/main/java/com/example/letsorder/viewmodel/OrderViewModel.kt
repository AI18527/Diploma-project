package com.example.letsorder.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.util.FirebaseDatabaseSingleton
import com.example.letsorder.model.Order
import com.example.letsorder.model.OrderDetails
import com.example.letsorder.util.RestaurantInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime

class OrderViewModel : ViewModel() {

    private var _orderDetails = MutableLiveData<List<OrderDetails>>()
    val orderDetails: LiveData<List<OrderDetails>>
        get() = _orderDetails

    private var _bill = MutableLiveData<Double>()
    val bill: LiveData<Double>
        get() = _bill

    private var order = Order()

    private val ref = FirebaseDatabaseSingleton.getInstance()
    private lateinit var listener: ValueEventListener

    fun getOrder(tableNum: Int) {
        val query =
            ref.getReference("/publicOrders/").orderByChild("tableNum").equalTo(tableNum.toDouble())
        listener = query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    if (snapshot.child("restaurantId").value.toString() == RestaurantInfo.restaurantId.toString()) {
                        val value = snapshot.getValue(Order::class.java)
                        value?.let {
                            _orderDetails.postValue(it.dishes)
                            _bill.postValue(it.bill)
                            order = it
                        }
                        dataSnapshot.children.iterator().next()
                            .child("/flagForWaiter/").ref.setValue("SEEN")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "load:onCancelled", error.toException())
            }
        })
    }

    fun removeOrder(tableNum: Int) {
        val query =
            ref.getReference("/publicOrders/").orderByChild("tableNum").equalTo(tableNum.toDouble())

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val order = snapshot.getValue(Order::class.java)
                    order?.let {
                        if (it.restaurantId == RestaurantInfo.restaurantId) {
                            moveOrder(order)
                        }
                    }
                }
                dataSnapshot.children.iterator().next().ref.removeValue()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "load:onCancelled", error.toException())
            }
        })
    }

    fun deleteOrder() {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun moveOrder(order: Order) {
        ref.getReference("/orders/").push()
            .setValue(
                hashMapOf(
                    "endTime" to LocalDateTime.now(),
                    "restaurantId" to order.restaurantId,
                    "tableNum" to order.tableNum,
                    "dishes" to order.dishes,
                    "bill" to order.bill
                )
            )
    }

    fun removeListener() {
        ref.getReference("/publicOrders/").removeEventListener(listener)
    }
}