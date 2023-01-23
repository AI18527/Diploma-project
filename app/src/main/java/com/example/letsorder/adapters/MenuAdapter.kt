package com.example.letsorder.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.model.Dish
import com.example.letsorder.views.MenuFragmentDirections

class MenuAdapter(private val dataset: List<Dish>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button = view.findViewById<Button>(R.id.button_dish)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu_view, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val dish = dataset[position]
        Log.d("Adapter", "$dataset")
        holder.button.text = dish.title

        holder.button.setOnClickListener {

            val action = MenuFragmentDirections.actionMenuFragmentToDishFragment(dishId = dish.id)
            holder.view.findNavController().navigate(action)
        }
    }
}