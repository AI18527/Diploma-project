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

class LoginViewModel : ViewModel() {

    /*private var _admin = MutableLiveData<String>()
    val admin : LiveData<String>
        get() = _admin*/

    private val ref = FirebaseDatabaseSingleton.getInstance().getReference("/restaurants/")

    fun checkUser(email: String, navCallbackAdmin: () -> Unit, navCallbackWaiter: () -> Unit ){
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val admin = dataSnapshot.child("/admin/").value
                Log.d("TAG", "${email == admin.toString()} $email")
                if (email == admin.toString()){
                    navCallbackAdmin()
                }
                else{
                    navCallbackWaiter()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "loadPost:onCancelled", error.toException())
            }
        })
    }
}