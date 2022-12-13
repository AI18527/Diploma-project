package com.example.letsorder.clients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R

class MenuAdapter (context: Context):
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    val dishes = context.resources.getStringArray(R.array.menu).toList()


        class MenuViewHolder(val view: View) : RecyclerView.ViewHolder(view){
            val textView: TextView = view.findViewById(R.id.title)
            //val checkBox: CheckBox = view.findViewById(R.id.check_box)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu_view, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = dishes[position]
        holder.textView.text = item
    }

    override fun getItemCount(): Int = dishes.size
}