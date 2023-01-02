package com.example.letsorder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.views.MenuFragmentDirections

class MenuAdapter (context: Context):
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    val dishes = context.resources.getStringArray(R.array.menu).toList()

    class MenuViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.button_dish)
    }

    override fun getItemCount(): Int = dishes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu_view, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = dishes.get(position)
        holder.button.text = item.toString()

        holder.button.setOnClickListener {
            val action =
                MenuFragmentDirections.actionMenuFragmentToItemFragment()

            // Navigate using that action
            holder.view.findNavController().navigate(action)
        }
    }
}