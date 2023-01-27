package com.example.letsorder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.model.Dish
import com.example.letsorder.views.SummaryEditListener
import java.text.NumberFormat

class SummaryOrderAdapter(dataset: Map<Dish, Int>, val summaryListener: SummaryEditListener) :
    RecyclerView.Adapter<SummaryOrderAdapter.SummaryOrderViewHolder>() {

    private var allDishes: MutableMap<Dish, Int> = dataset as MutableMap<Dish, Int>
    private val dishes: MutableSet<Dish> = allDishes.keys

    class SummaryOrderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.title)
        val price = view.findViewById<TextView>(R.id.price)
        val quantity = view.findViewById<TextView>(R.id.quantity)
        val buttonAdd = view.findViewById<Button>(R.id.buttonAdd)
        val buttonSub = view.findViewById<Button>(R.id.buttonSubtract)

    }

    override fun getItemCount(): Int = dishes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryOrderViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_summary, parent, false)
        return SummaryOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SummaryOrderViewHolder, position: Int) {
        val dish = dishes.elementAt(position)
        holder.title.text = dish.title
        holder.price.text = NumberFormat.getCurrencyInstance().format(dish.price).toString()
        holder.quantity.text = "Quantity: ${allDishes.getValue(dish)}"

        holder.buttonAdd.setOnClickListener {
            summaryListener.dishAdd(dish)
        }
        holder.buttonSub.setOnClickListener {
            summaryListener.dishSub(dish)
        }
    }

    fun updateData(data: Map<Dish, Int>) {

        allDishes.putAll(data)
        notifyDataSetChanged()
    }
}