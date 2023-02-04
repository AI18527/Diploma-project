package com.example.letsorder.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.model.OrderDetails

class CurrOrderAdapter(dataset: List<OrderDetails>) :
    RecyclerView.Adapter<CurrOrderAdapter.CurrOrderViewHolder>() {

    private var allDishes: List<OrderDetails> = dataset

    class CurrOrderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.title)
        //val price = view.findViewById<TextView>(R.id.price)
        val quantity = view.findViewById<TextView>(R.id.quantity)
        val buttonAdd = view.findViewById<Button>(R.id.buttonAdd)
        val buttonSub = view.findViewById<Button>(R.id.buttonSubtract)
    }

    override fun getItemCount(): Int = allDishes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrOrderViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_summary, parent, false)
        return CurrOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrOrderViewHolder, position: Int) {
        val dish = allDishes[position]
        holder.title.text = dish.dish
        //holder.price.text = NumberFormat.getCurrencyInstance().format(dish.price).toString()
        holder.quantity.text = dish.quantity.toString()

        holder.buttonAdd.visibility =  View.INVISIBLE
        holder.buttonSub.visibility = View.INVISIBLE

    }
}