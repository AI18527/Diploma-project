package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.util.FirebaseDatabaseSingleton
import com.example.letsorder.util.RestaurantInfo
import com.example.letsorder.model.Table
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TablesEditViewModel : ViewModel() {

    private var _tables = MutableLiveData<List<Table>>()
    val tables: LiveData<List<Table>>
        get() = _tables

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("tables")
    private lateinit var listener: ValueEventListener

    private var listenerTables : ValueEventListener = ref.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val tables = arrayListOf<Table>()
            for (snapshot in dataSnapshot.children) {
                val value = snapshot.getValue(Table::class.java)
                value?.let{
                    if (it.restaurantId == RestaurantInfo.restaurantId) {
                        tables.add(it)
                    }
                }
            }
            _tables.postValue(tables.sortedBy { it.tableNum })
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("Error", "load:onCancelled", databaseError.toException())
        }
    })

    fun addTable(tableNum:Int, capacity: Int) {
        val table = Table(tableNum = tableNum, capacity = capacity, restaurantId = RestaurantInfo.restaurantId)
        ref.push().setValue(table)
    }

    fun deleteTable(table: Int) {
        val query = ref.orderByChild("tableNum").equalTo(table.toDouble())
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    if (snapshot.child("restaurantId").value.toString() == RestaurantInfo.restaurantId.toString())
                        snapshot.ref.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "load:onCancelled", error.toException())
            }
        })
    }

    fun removeListener(){
        ref.removeEventListener(listener)
        ref.removeEventListener(listenerTables)
    }
}