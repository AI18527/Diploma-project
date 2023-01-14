package com.example.letsorder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.model.Dish
import java.text.NumberFormat

class SummaryOrderAdapter (private val dataset: List<Dish>):
    RecyclerView.Adapter<SummaryOrderAdapter.SummaryOrderViewHolder>(){

    class SummaryOrderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.title)
        val prize = view.findViewById<TextView>(R.id.prize)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryOrderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_summary, parent, false)
        return SummaryOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SummaryOrderViewHolder, position: Int) {
        val dish = dataset[position]
        holder.title.text = dish.title
        holder.prize.text = NumberFormat.getCurrencyInstance().format(dish.prize).toString()
    }
}