package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.model.Table
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TablesEditViewModel : ViewModel() {

    private var _existingTable = MutableLiveData<Boolean>()
    val existingTable : LiveData<Boolean>
    get() = _existingTable

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("tables")

    fun doesTableExist(tableNum : Int){
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val value = snapshot.getValue(Table::class.java)
                    value?.let {
                        if (it.tableNum == tableNum) {
                            _existingTable.postValue(true)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "load:onCancelled", error.toException())
            }
        })
    }
    fun addTable(tableNum:Int, capacity: Int) {
        val table = Table(tableNum = tableNum, capacity = capacity, restaurantId = 1)
        ref.push().setValue(table)
    }
}