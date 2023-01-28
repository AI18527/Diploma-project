package com.example.letsorder.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.PackageManagerCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.model.Dish
import com.example.letsorder.model.Waiter
import com.example.letsorder.viewmodel.MenuViewModel
import com.example.letsorder.views.client.MenuFragmentDirections

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
        val buttonDish = view.findViewById<Button>(R.id.button_dish)!!
    }

    override fun getItemCount(): Int = menu.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu_view, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val dish = menu[position]

        holder.buttonDish.text = dish.title
        holder.buttonDish.setOnClickListener {
            holder.view.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToDishFragment(dishId = dish.id, dishTitle = dish.title))
        }
    }

}