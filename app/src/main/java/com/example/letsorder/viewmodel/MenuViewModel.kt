package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.util.FirebaseDatabaseSingleton
import com.example.letsorder.util.RestaurantInfo
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
                    if (it.restaurantId == RestaurantInfo.restaurantId){
                        dishes.add(it)
                    }
                }
            }
            dishes = dishes.sortedBy{it.category}.reversed() as ArrayList<Dish>
            _menu.postValue(dishes)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("Error", "load:onCancelled", databaseError.toException())
        }
    })

    fun deleteDish(dish: String) {
        val query = ref.orderByChild("title").equalTo(dish)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children){
                    if (snapshot.child("restaurantId").value.toString() == RestaurantInfo.restaurantId.toString())
                        snapshot.ref.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "load:onCancelled", error.toException())
            }
        })
    }

    fun removeListener() {
        ref.removeEventListener(listener)
    }
}