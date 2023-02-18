package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.model.Waiter
import com.example.letsorder.util.Event
import com.example.letsorder.util.FirebaseDatabaseSingleton
import com.example.letsorder.util.RestaurantInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    private val ref = FirebaseDatabaseSingleton.getInstance()
    private var auth = Firebase.auth

    private lateinit var listenerRestaurant: ValueEventListener
    private lateinit var listenerWaiters: ValueEventListener

    val isAdmin = MutableLiveData<Event<Boolean>>()
    val isWaiter = MutableLiveData<Event<Boolean>>()
    val rightLogin = MutableLiveData<Event<Boolean>>()

    fun onStateChangedWaiter(newState: Boolean) {
        isWaiter.postValue(Event(newState))
    }

    fun onStateChangedAdmin(newState: Boolean) {
        isAdmin.postValue(Event(newState))
    }

    fun onStateChangedLogin(newState: Boolean) {
        rightLogin.postValue(Event(newState))
    }

    fun isAdmin(email: String, password: String) {
        listenerRestaurant =
            ref.getReference("/restaurants/").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val admin = dataSnapshot.child("/admin/").value
                    if (email == admin.toString()) {
                        singIn(email, password, "Admin")
                    } else {
                        onStateChangedAdmin(false)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("TAG", "loadPost:onCancelled", error.toException())
                }
            })
    }

    fun isWaiter(email: String, password: String) {
        val query = ref.getReference("/waiters/").orderByChild("/email/")
            .equalTo(email).limitToFirst(1)
        listenerWaiters = query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.value == null){
                    onStateChangedWaiter(false)
                }
                else {
                    dataSnapshot.children.map {
                        if (it.child("restaurantId").value.toString() != RestaurantInfo.restaurantId.toString()) {
                            onStateChangedWaiter(false)
                        } else {
                            singIn(email, password, "Waiter")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "load:onCancelled", error.toException())
            }
        })
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")
                    onStateChangedWaiter(true)
                } else {
                    onStateChangedLogin(false)
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                }
            }
    }

    private fun singIn(email: String, password: String, role: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    if (role == "Waiter")
                        onStateChangedWaiter(true)
                    else if (role == "Admin")
                        onStateChangedAdmin(true)
                } else {
                    if (role == "Waiter") {
                        register(email, password)
                    } else if (role == "Admin") {
                        onStateChangedLogin(false)
                    }
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                }
            }
    }

    fun removeListeners(){
        ref.getReference("/restaurants/").removeEventListener(listenerRestaurant)
        ref.getReference("/waiters/").removeEventListener(listenerWaiters)
    }
}