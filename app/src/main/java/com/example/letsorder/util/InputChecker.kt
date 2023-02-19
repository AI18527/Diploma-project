package com.example.letsorder.util

class InputChecker {
    fun checkInput(elements: ArrayList<String>): Boolean {
        for (element in elements){
            if (element == ""){
                return false
            }
        }
        return true
    }
}