package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.model.Dish
import com.example.letsorder.model.Order
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TablesViewModel : ViewModel() {
    private val _tables = MutableLiveData<List<Order>>()
    val tables: LiveData<List<Order>>
        get() = _tables

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("/publicOrders/")

    private var listener : ValueEventListener = ref.addValueEventListener(object :
        ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val tables = arrayListOf<Order>()
            for (snapshot in dataSnapshot.children) {
                val value = snapshot.getValue(Order::class.java)
                Log.d("TABLE", "$value")
                if (value != null) {
                    tables.add(value)
                }
            }
            _tables.postValue(tables)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("Error", "load:onCancelled", databaseError.toException())
        }
    })

    fun removeListener() {
        ref.removeEventListener(listener)
    }
}