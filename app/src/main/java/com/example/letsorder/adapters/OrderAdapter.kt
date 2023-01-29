package com.example.letsorder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.model.*

class OrderAdapter (dataset: List<OrderDetails>):
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private val orders = ArrayList<OrderDetails>().apply { addAll(dataset) }
    class OrderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val dishTitle = view.findViewById<TextView>(R.id.dishTitle)
        val dishQuantity = view.findViewById<TextView>(R.id.dishQuantity)
    }

    override fun getItemCount(): Int = orders.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order_view, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val dish = orders[position]
        holder.dishTitle.text = dish.dish
        holder.dishQuantity.text = dish.quantity.toString()
    }
}