package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.model.Waiter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class WaiterRegisterViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val ref = FirebaseDatabaseSingleton.getInstance()

    private var _waiters = MutableLiveData<List<Waiter>>()
    val waiters: LiveData<List<Waiter>>
        get() = _waiters

    /*private val listener: ValueEventListener =
        ref.getReference("/waiters/").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val waiters = arrayListOf<Waiter>()
                for (snapshot in dataSnapshot.children) {
                    val value = snapshot.getValue(Waiter::class.java)
                    if (value != null) {
                        waiters.add(value)
                    }
                }
                _waiters.postValue(waiters)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "load:onCancelled", error.toException())
            }

        })*/

    fun addWaiter(name: String, email: String) {
        Log.d("TAG", "${auth.currentUser!!.uid}")
        val newWaiter = hashMapOf(
            "waiterName" to name,
            "email" to email,
            "restaurantId" to 1
        )
        ref.getReference("/waiters/").push().setValue(newWaiter)
    }

    fun deleteWaiter(waiter: Waiter) {
        val query =
            ref.getReference("/waiters/").orderByChild("email").equalTo(waiter.email)
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

    private fun password(): String = List(8) {
        (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
    }.joinToString("")

//
//    fun loadWaiters(): List<Waiter> {
//        return Datasource.waiters
//    }
}