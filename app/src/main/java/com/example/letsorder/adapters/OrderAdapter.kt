package com.example.letsorder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.model.Dish
import com.example.letsorder.model.Table
import com.example.letsorder.views.TablesFragmentDirections

class OrderAdapter (private val dataset: List<Dish>):
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val dishTitle = view.findViewById<TextView>(R.id.dishTitle)
        val dishPrice = view.findViewById<TextView>(R.id.dishPrice)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order_view, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val dish = dataset[position]
        holder.dishTitle.text = dish.title
        holder.dishPrice.text = dish.price.toString()
    }
}