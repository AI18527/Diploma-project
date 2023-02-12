package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.data.FirebaseDatabaseSingleton
import com.example.letsorder.model.Dish
import com.example.letsorder.model.Waiter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class WaiterRegisterViewModel : ViewModel() {

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("/waiters/")

    private var _waiters = MutableLiveData<List<Waiter>>()
    val waiters: LiveData<List<Waiter>>
        get() = _waiters

    private var listener : ValueEventListener = ref.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            var waiters = arrayListOf<Waiter>()
            for (snapshot in dataSnapshot.children) {
                val value = snapshot.getValue(Waiter::class.java)
                value?.let{
                    waiters.add(it)
                }
            }
            _waiters.postValue(waiters)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("Error", "load:onCancelled", databaseError.toException())
        }
    })

    fun addWaiter(name: String, email: String) {
        val newWaiter = hashMapOf(
            "waiterName" to name,
            "email" to email,
            "restaurantId" to 1
        )
        ref.push().setValue(newWaiter)
    }

    fun deleteWaiter(waiter: Waiter) {
        val query = ref.orderByChild("email").equalTo(waiter.email)
                .limitToFirst(1)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.iterator().next().ref.removeValue()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "load:onCancelled", error.toException())
            }
        })
    }
}