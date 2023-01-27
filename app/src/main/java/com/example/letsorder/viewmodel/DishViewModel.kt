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

    private lateinit var listener : ValueEventListener

    fun getDish(id: Int){
        listener = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val value = snapshot.getValue(Dish::class.java)
                    value?.let {
                        if (it.id == id) {
                            _dish.postValue(it)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Error", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    fun removeListener(){
        ref.removeEventListener(listener)
    }
}