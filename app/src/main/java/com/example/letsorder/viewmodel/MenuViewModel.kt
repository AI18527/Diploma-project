package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.data.FirebaseDatabaseSingleton
import com.example.letsorder.model.Dish
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class MenuViewModel : ViewModel() {
    private val _menu = MutableLiveData<List<Dish>>()
    val menu: LiveData<List<Dish>>
        get() = _menu

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("/dishes/")

    private var listener : ValueEventListener = ref.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            var dishes = arrayListOf<Dish>()
            for (snapshot in dataSnapshot.children) {
                val value = snapshot.getValue(Dish::class.java)
                value?.let{
                    if (it.restaurantId == TableStatusViewModel().restaurantId){
                        dishes.add(it)
                    }
                }
            }
            _menu.postValue(dishes)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("Error", "load:onCancelled", databaseError.toException())
        }
    })

    fun removeListener() {
        ref.removeEventListener(listener)
    }
}