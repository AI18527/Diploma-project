package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.data.Datasource
import com.example.letsorder.model.Order
import com.example.letsorder.model.OrderDetails
import com.example.letsorder.viewmodel.SummaryViewModel.Companion.active
import com.example.letsorder.views.SummaryOrderFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class QRViewModel: ViewModel() {
    private var _freeTable = MutableLiveData<Boolean>(true)
    val freeTable : LiveData<Boolean>
        get() = _freeTable

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("/publicOrders/")
    private lateinit var listener : ValueEventListener

    fun isTableFree(tableNum: Int){
        listener = ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children){
                    val value = snapshot.getValue(Order::class.java)
                    value?.let {
                        if (it.tableNum == tableNum) {
                            _freeTable.postValue(false)
                            Datasource.currOrder = it
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "load:onCancelled", error.toException())
            }
        })
    }
}
