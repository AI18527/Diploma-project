package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.util.Event
import com.example.letsorder.util.FirebaseDatabaseSingleton
import com.example.letsorder.model.Order
import com.example.letsorder.model.Table
import com.example.letsorder.util.RestaurantInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TableStatusViewModel : ViewModel() {
    private var _tableOrder = MutableLiveData<Order>()
    val tableOrder: LiveData<Order>
        get() = _tableOrder

    val freeTable: Boolean
        get() = FREETABLE

    val tableNum: Int
        get() = TABLENUM

    val tableExists = MutableLiveData<Event<Boolean>>()
    val isFree = MutableLiveData<Event<Boolean>>()

    fun onStateChanged(newState: Boolean) {
        isFree.postValue(Event(newState))
    }

    fun onStateChangedTable(newState: Boolean){
        tableExists.postValue(Event(newState))
    }

    private val ref = FirebaseDatabaseSingleton.getInstance()
    private lateinit var listenerPublicOrders: ValueEventListener
    private lateinit var listenerTables: ValueEventListener
    private lateinit var listener: ValueEventListener

    fun getTable(restaurantId: Int, tableNum: Int) {
        listenerTables =
            ref.getReference("/tables/").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val value = snapshot.getValue(Table::class.java)
                        value?.let {
                            if (it.tableNum == tableNum && it.restaurantId == restaurantId) {
                                RestaurantInfo.restaurantId = restaurantId
                                TABLENUM = tableNum
                                onStateChangedTable(true)
                            }
                        }
                    }
                    if (RestaurantInfo.restaurantId == 0 && TABLENUM == 0){
                        onStateChangedTable(false)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w("Error", "load:onCancelled", error.toException())
                }
            })
    }

    fun isTableFree() {
        val query = ref.getReference("publicOrders").orderByChild("restaurantId").equalTo(RestaurantInfo.restaurantId.toDouble())
        listener = query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    if (snapshot.child("tableNum").value.toString() == tableNum.toString()) {
                        FREETABLE = false
                        onStateChanged(false)
                    }
                    else FREETABLE = true
                }
                if (FREETABLE){
                    onStateChanged(true)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "load:onCancelled", error.toException())
            }
        })
    }

    fun getOrder() {
        val query =
            ref.getReference("/publicOrders/").orderByChild("tableNum").equalTo(tableNum.toDouble())
                .limitToFirst(1)
        listenerPublicOrders = query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.map {
                    it.getValue(Order::class.java)
                }.firstOrNull()?.let {
                    _tableOrder.postValue(it)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("TAG", "load:onCancelled", databaseError.toException())
            }
        })
    }

    fun takeTable() {
        FREETABLE = false
    }

    fun removeListeners() {
        ref.getReference("/tables/").removeEventListener(listenerTables)
        ref.getReference("/publicOrders/").removeEventListener(listener)
    }

    companion object {
        private var TABLENUM: Int = 0
        var FREETABLE: Boolean = true
    }
}
