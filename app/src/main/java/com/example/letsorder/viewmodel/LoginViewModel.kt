package com.example.letsorder.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.letsorder.data.FirebaseDatabaseSingleton
import com.example.letsorder.model.Waiter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    private val ref = FirebaseDatabaseSingleton.getInstance()
    private var auth = Firebase.auth

    private lateinit var listenerRestaurant :ValueEventListener
    private lateinit var listenerWaiters: ValueEventListener

    val isWaiter : Boolean
    get() = IS_WAITER

    fun checkUser(email: String, password: String, navCallbackAdmin: () -> Unit, navCallbackWaiter: () -> Unit) {
        listenerRestaurant = ref.getReference("/restaurants/").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val admin = dataSnapshot.child("/admin/").value
                if (email == admin.toString()) {
                    auth.signInWithEmailAndPassword(email, password)
                    navCallbackAdmin()
                } else {
                    isWaiter(email, password, navCallbackWaiter)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "loadPost:onCancelled", error.toException())
            }
        })
    }

    fun isWaiter(email: String,password: String, navCallbackWaiter: () -> Unit) {
        listenerWaiters = ref.getReference("/waiters/").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val value = snapshot.getValue(Waiter::class.java)
                        value?.let {
                            if (it.email == email) {
                                IS_WAITER = true
                                singIn(email, password, navCallbackWaiter)
                            }
                        }
                    }
                    if (!IS_WAITER) {
                        Log.d("TAG", "not a waiter")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("Error", "load:onCancelled", error.toException())
                }
            })
    }

    private fun register(email: String, password: String, navCallbackWaiter: () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")
                    navCallbackWaiter()
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                }
            }
    }

    private fun singIn(email: String, password: String, navCallbackWaiter: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    navCallbackWaiter()
                } else {
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    register(email, password, navCallbackWaiter)
                }
            }
    }

    companion object{
        var IS_WAITER = false
    }
}