package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.model.Order
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TablesViewModel : ViewModel() {
    private val _tables = MutableLiveData<Map<Int, Boolean>>()
    val tables: LiveData<Map<Int, Boolean>>
        get() = _tables

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("/publicOrders/")

    private var listener : ValueEventListener = ref.addValueEventListener(object :
        ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val tables = mutableMapOf<Int, Boolean>()
            for (snapshot in dataSnapshot.children) {
                val value = snapshot.getValue(Order::class.java)
                value?.let {
                    tables[value.tableNum] = value.flagForWaiter
                }
            }
            _tables.postValue(tables)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("Error", "load:onCancelled", error.toException())
        }
    })

    fun removeListener() {
        ref.removeEventListener(listener)
    }
}