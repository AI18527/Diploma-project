package com.example.letsorder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsorder.data.Datasource
import com.example.letsorder.model.Dish


class SummaryViewModel : ViewModel() {
    private var map = Datasource().loadLocalOrder()

    private var _bill = MutableLiveData<Double>()
    val bill: LiveData<Double>
        get() = _bill

    fun updateData(updatedMap :Map<Dish, Int>){
        map = updatedMap
        sumBill()
    }

    fun sumBill() {
        var currBill = 0.0
        for (item in map.keys){
            currBill += item.price * map[item]!!
        }
        _bill.postValue(currBill)
    }

}