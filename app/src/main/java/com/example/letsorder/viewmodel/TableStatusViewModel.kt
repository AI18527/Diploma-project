package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.data.Event
import com.example.letsorder.data.FirebaseDatabaseSingleton
import com.example.letsorder.model.Order
import com.example.letsorder.model.Table
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

    val isFree = MutableLiveData<Event<Boolean>>()

    fun onStateChanged(newState: Boolean) {
        isFree.postValue(Event(newState))
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
                                isTableFree(tableNum)
                            }
                            // event for existing
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w("Error", "load:onCancelled", error.toException())
                }
            })
    }

    fun isTableFree(tableNum: Int) {
        TABLENUM = tableNum
        val query = ref.getReference("/publicOrders/").orderByChild("/tableNum/")
            .equalTo(tableNum.toDouble()).limitToFirst(1)

        listener = query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.value == null) {
                    onStateChanged(true)
                    FREETABLE = true
                } else {
                    FREETABLE = false
                    onStateChanged(false)
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
