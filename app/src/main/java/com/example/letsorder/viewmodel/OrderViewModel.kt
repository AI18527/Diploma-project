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

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("/publicOrders/")

    private lateinit var listener : ValueEventListener

    fun getOrder(tableNum : Int){
        listener = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val value = snapshot.getValue(Order::class.java)
                    value?.let {
                        if (it.tableNum == tableNum) {
                            _order.postValue(it.dishes)
                            _bill.postValue(it.bill)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "load:onCancelled", error.toException())
            }
        })
    }

    fun moveOrder(){
        val refOrder = FirebaseDatabaseSingleton.getInstance().getReference("orders")
    }

    fun removeListener(){
        ref.removeEventListener(listener)
    }
}