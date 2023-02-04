package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.data.Datasource.Companion.free
import com.example.letsorder.data.Datasource.Companion.tableNum
import com.example.letsorder.model.Dish
import com.example.letsorder.model.Order
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TableStatusViewModel: ViewModel() {
    private var _tableOrder = MutableLiveData<Order>()
    val tableOrder : LiveData<Order>
    get() = _tableOrder

    val freeTable : Boolean
        get() = FREETABLE

    val tableNum : Int
        get() = TABLENUM

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("/publicOrders/")
    private lateinit var listener : ValueEventListener

    fun isTableFree(tableNum: Int, navCallback: () -> Unit){
        listener = ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                TABLENUM = tableNum
                for (snapshot in dataSnapshot.children){
                    val value = snapshot.getValue(Order::class.java)
                    value?.let {
                        if (it.tableNum == tableNum) {
                            FREETABLE = false
                            navCallback()
                        }
                    }
                }
                if(free){
                    navCallback()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "load:onCancelled", error.toException())
            }
        })
    }

    fun getOrder(){
        val query =  ref.orderByChild("tableNum").equalTo(tableNum.toDouble()).limitToFirst(1)
        query.addValueEventListener(object : ValueEventListener {
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

    fun takeTable(){
        FREETABLE = false
    }

    companion object{
        private var TABLENUM : Int = 0
        private var FREETABLE : Boolean = true
    }
}
