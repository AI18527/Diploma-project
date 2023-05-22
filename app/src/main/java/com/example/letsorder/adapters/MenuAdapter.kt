package com.example.letsorder.adapters

import android.nfc.cardemulation.CardEmulation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.letsorder.R
import com.example.letsorder.model.Dish
import com.example.letsorder.viewmodel.MenuViewModel
import com.example.letsorder.views.admin.DishEditListener
import com.example.letsorder.views.client.MenuFragmentDirections
import com.example.letsorder.views.client.SummaryEditListener
import java.text.NumberFormat

import kotlin.collections.ArrayList

class MenuAdapter(viewModel: MenuViewModel) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val menu = ArrayList<Dish>()

    init {
        viewModel.menu.observeForever {
            menu.clear()
            menu.addAll(it)
            notifyDataSetChanged()
        }
    }

    class MenuViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val cardDish = view.findViewById<CardView>(R.id.card)!!
        val title = view.findViewById<TextView>(R.id.title)!!
        val category = view.findViewById<TextView>(R.id.category)!!
        val price = view.findViewById<TextView>(R.id.price)!!
    }

    override fun getItemCount(): Int = menu.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu_view, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val dish = menu[position]

        holder.title.text = dish.title
        holder.category.text = dish.category
        holder.price.text = NumberFormat.getCurrencyInstance().format(dish.price).toString()

        holder.cardDish.setOnClickListener {
            holder.view.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToDishFragment(dishTitle = dish.title))
        }
    }

}