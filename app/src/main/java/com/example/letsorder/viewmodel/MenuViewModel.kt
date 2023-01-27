package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.model.Dish
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class MenuViewModel : ViewModel() {
    private val _menu = MutableLiveData<List<Dish>>()
    val menu: LiveData<List<Dish>>
        get() = _menu

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("/menus/0/dishes")

    private var listener : ValueEventListener = ref.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val menu = arrayListOf<Dish>()
            for (snapshot in dataSnapshot.children) {
                val value = snapshot.getValue(Dish::class.java)
                if (value != null) {
                    menu.add(value)
                }
            }
            _menu.postValue(menu)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("Error", "loadPost:onCancelled", databaseError.toException())
        }
    })

    fun removeListener() {
        ref.removeEventListener(listener)
    }
}