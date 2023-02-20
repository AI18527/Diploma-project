package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
                        singInAdmin(email, password)
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
                            singInWaiter(email, password)
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
                    onStateChangedWaiter(true)
                } else {
                    onStateChangedLogin(false)
                }
            }
    }

    private fun singInAdmin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onStateChangedAdmin(true)
                } else {
                    onStateChangedLogin(false)
                }
            }
    }

    private fun singInWaiter(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onStateChangedWaiter(true)
                } else {
                    register(email, password)
                }
            }
    }

    fun removeListeners(){
        if (this::listenerRestaurant.isInitialized) {
            ref.getReference("/restaurants/").removeEventListener(listenerRestaurant)
        }
        if (this::listenerWaiters.isInitialized){
            ref.getReference("/waiters/").removeEventListener(listenerWaiters)
        }
    }
}