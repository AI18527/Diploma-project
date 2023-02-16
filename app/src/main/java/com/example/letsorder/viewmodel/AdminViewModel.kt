package com.example.letsorder.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.util.FirebaseDatabaseSingleton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime

class AdminViewModel : ViewModel(){

    private val _ordersNum = MutableLiveData<Int>()
    val ordersNum: LiveData<Int>
        get() = _ordersNum

    private val ref = FirebaseDatabaseSingleton.getInstance()

    private lateinit var listenerOrders : ValueEventListener

    fun getOrders(restaurantId: Int){
        listenerOrders =  ref.getReference("/orders/").addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var ordersCounter = 0
                for(snapshot in dataSnapshot.children){
                    if (snapshot.child("restaurantId").value.toString() == restaurantId.toString()){
                        if (snapshot.child("/endTime/").child("/month/").value.toString() == LocalDateTime.now().month.toString())
                            ordersCounter += 1
                    }
                }
                _ordersNum.postValue(ordersCounter)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("TAG", "load:onCancelled", databaseError.toException())
            }
        })
    }

    fun removeListener(){
        ref.getReference("/orders/").removeEventListener(listenerOrders)
    }
}