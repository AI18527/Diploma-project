package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.data.Datasource
import com.example.letsorder.model.Dish
import com.example.letsorder.model.Waiter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class WaiterEditViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val ref = FirebaseDatabaseSingleton.getInstance()

    private var _waiters = MutableLiveData<List<Waiter>>()
    val waiters: LiveData<List<Waiter>>
        get() = _waiters

    private val listener : ValueEventListener = ref.getReference("/waiters/").addValueEventListener(object : ValueEventListener{
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

    })

    fun addWaiter(name: String, email : String) {
        auth.createUserWithEmailAndPassword(email, "123456")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")
                    ref.getReference("/waiters/")
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                }
            }
    }

//    fun deleteWaiter(waiter: Waiter): List<Waiter> {
//        Datasource.waiters.remove(waiter)
//        return Datasource.waiters
//    }
//
//    fun loadWaiters(): List<Waiter> {
//        return Datasource.waiters
//    }
}