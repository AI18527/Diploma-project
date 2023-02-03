package com.example.letsorder.viewmodel

import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.R
import com.example.letsorder.data.Datasource
import com.example.letsorder.data.Datasource.Companion.currOrder
import com.example.letsorder.data.Datasource.Companion.free
import com.example.letsorder.data.Datasource.Companion.tableNum
import com.example.letsorder.model.Order
import com.example.letsorder.views.QRFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class QRViewModel: ViewModel() {
    private var _freeTable = MutableLiveData(true)
    val freeTable : LiveData<Boolean>
        get() = _freeTable

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("/publicOrders/")
    private lateinit var listener : ValueEventListener

    fun isTableFree(tableNum: Int, navCallback: () -> Unit){
        listener = ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Datasource.tableNum = tableNum
                for (snapshot in dataSnapshot.children){
                    val value = snapshot.getValue(Order::class.java)
                    value?.let {
                        if (it.tableNum == tableNum) {
                            _freeTable.postValue(false)
                            free = false
                            Datasource.currOrder = it // shared
                            Datasource.tableNum = 0
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
}
