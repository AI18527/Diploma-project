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


class DishViewModel() : ViewModel(){

    private val _dish = MutableLiveData<Dish>()
    val dish: LiveData<Dish>
    get() = _dish

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("/menus/0/dishes/")

    fun getDish(title: String){
        val query =  ref.orderByChild("title").equalTo(title).limitToFirst(1)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.map {
                    it.getValue(Dish::class.java)
                }.firstOrNull()?.let {
                    _dish.postValue(it)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("TAG", "load:onCancelled", databaseError.toException())
            }
        })
    }
}