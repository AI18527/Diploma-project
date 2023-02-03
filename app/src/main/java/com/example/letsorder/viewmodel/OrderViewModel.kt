package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.model.Order
import com.example.letsorder.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class OrderViewModel: ViewModel() {

    private var _order = MutableLiveData<List<OrderDetails>>()
    val order: LiveData<List<OrderDetails>>
        get() = _order

    private var _bill = MutableLiveData<Double>()
    val bill: LiveData<Double>
        get() = _bill

    private val ref = FirebaseDatabaseSingleton.getInstance()

    fun getOrder(tableNum : Int){
        val query =  ref.getReference("/publicOrders/").orderByChild("tableNum").equalTo(tableNum.toDouble()).limitToFirst(1)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.map {
                    it.getValue(Order::class.java)
                }.firstOrNull()?.let {
                    _order.postValue(it.dishes)
                    _bill.postValue(it.bill)
                    query.removeEventListener(this)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "load:onCancelled", error.toException())
            }
        })
    }

    fun removeOrder(tableNum: Int){
        val query = ref.getReference("/publicOrders/").orderByChild("tableNum").equalTo(tableNum.toDouble()).limitToFirst(1)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.map{
                    val order = it.getValue(Order::class.java)
                    ref.getReference("/orders/").push().setValue(order)
                }
                dataSnapshot.children.iterator().next().ref.removeValue()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "load:onCancelled", error.toException())
            }
        })
    }
}