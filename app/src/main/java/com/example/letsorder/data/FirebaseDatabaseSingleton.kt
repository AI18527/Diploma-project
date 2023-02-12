package com.example.letsorder.data

import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseSingleton private constructor() {
    companion object {
        private var instance: FirebaseDatabase? = null
        fun getInstance(): FirebaseDatabase {
            if (instance == null) {
                instance = FirebaseDatabase.getInstance("https://diploma-project-lets-order-default-rtdb.europe-west1.firebasedatabase.app")
            }
            return instance!!
        }
    }
}

