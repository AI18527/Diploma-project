package com.example.letsorder.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.data.FirebaseDatabaseSingleton
import com.example.letsorder.model.Order
import com.example.letsorder.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.LocalTime

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
        val query = ref.getReference("/publicOrders/").orderByChild("tableNum").equalTo(tableNum.toDouble())
                .limitToFirst(1)
        listener = query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.map {
                    it.getValue(Order::class.java)
                }.firstOrNull()?.let {
                    _orderDetails.postValue(it.dishes)
                    _bill.postValue(it.bill)
                    order = it

                    dataSnapshot.children.iterator().next().child("/flagForWaiter/").ref.setValue("SEEN")
                    query.removeEventListener(this)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "load:onCancelled", error.toException())
            }
        })
    }

    fun removeOrder(tableNum: Int) {
        val query = ref.getReference("/publicOrders/").orderByChild("tableNum").equalTo(tableNum.toDouble())
                .limitToFirst(1)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.map {
                    val order = it.getValue(Order::class.java)
                    order?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
                    }
                }
                dataSnapshot.children.iterator().next().ref.removeValue()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "load:onCancelled", error.toException())
            }
        })
    }

    fun removeListener(){
        ref.getReference("/publicOrders/").removeEventListener(listener)
    }
}