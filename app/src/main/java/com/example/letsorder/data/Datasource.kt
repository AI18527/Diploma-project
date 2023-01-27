package com.example.letsorder.data

import com.example.letsorder.FirebaseDatabaseSingleton
import com.example.letsorder.model.Dish
import com.example.letsorder.model.Order
import com.example.letsorder.model.Table
import com.example.letsorder.model.Waiter


class Datasource {

    //for waiter
    fun loadTables(): List<Table> {
        return tables
    }

    fun loadOrderForTable(tableNum: Int): List<Dish> {
        return tables.find { table -> table.number == tableNum }!!.order

    }

    fun removeTable(){
        //TODO: remove the ready table from the firebase, so each table can be there only once
    }

    //for client
    fun addOrder(tableNum: Int, order: List<Dish>) {
        //tables.add(Table(tableNum, order, true))
    }

    fun addDishToLocalOrder(dish: Dish): Map<Dish, Int> {
        if (!localOrder.active){
            if (localOrder.dishes.containsKey(dish)) {
                localOrder.dishes[dish] = localOrder.dishes.getValue(dish) + 1
            } else {
                localOrder.dishes[dish] = 1
            }
        }
        return localOrder.dishes
    }

    fun removeDishFromLocalOrder(dish: Dish) : Map<Dish, Int> {
        if (localOrder.dishes.getValue(dish) == 1) {
            localOrder.dishes.remove(dish)
        } else {
            localOrder.dishes[dish] = localOrder.dishes.getValue(dish) - 1
        }
        return localOrder.dishes
    }

    fun loadLocalOrder(): Map<Dish, Int> {
        return localOrder.dishes
    }

    fun sendOrder(){
        localOrder.active = true
        //TODO send it to the firebase
    }

    fun createLocalOrder() {
        localOrder = Order(1) //from the qr/ instead of id
    }

    //for admin
    fun addDishToMenu(category: String, title: String, description: String, price: Double) {
        val dishesRef = database.getReference("menus/0/dishes")
        val nextId = (1..10000).random()
        //TODO: check if id exists
        val newDish = Dish(category, nextId, title, description, price)
        val newDishRef = dishesRef.push()
        newDishRef.setValue(newDish)
    }

    fun addWaiter(waiter: Waiter) {
        waiters.add(waiter)
    }

    fun deleteWaiter(waiter: Waiter): List<Waiter> {
        waiters.remove(waiter)
        return waiters
    }

    fun loadWaiters(): List<Waiter> {
        return waiters
    }

    companion object {
        private var database = FirebaseDatabaseSingleton.getInstance()
        //TODO:
        // it is going to stay here and when the client clicked "order" it is going to be add to the public order // for one person only at first
        // move to public firebase order // add listener to the waiter to see the changes and move the order to private orders
        private var localOrder = Order()

        private val waiters = mutableListOf(Waiter("waiter1", "1234"))
        private val tables = mutableListOf(
            Table(
                1,
                listOf(Dish("Soups", 1, "Tomato soup", "Creamy tomato soup", 7.60)),
                true
            )
        )
    }
}