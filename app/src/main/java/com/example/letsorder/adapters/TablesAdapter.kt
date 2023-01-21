package com.example.letsorder.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.model.Table
import com.example.letsorder.views.TablesFragmentDirections

class TablesAdapter (private val dataset: List<Table>):
    RecyclerView.Adapter<TablesAdapter.TablesViewHolder>() {

        class TablesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val button = view.findViewById<Button>(R.id.buttonTable)
        }

        override fun getItemCount(): Int = dataset.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablesAdapter.TablesViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_table_view, parent, false)
            return TablesViewHolder(view)
        }

        override fun onBindViewHolder(holder: TablesViewHolder, position: Int) {
            val table = dataset[position]
            holder.button.text = "Table ${table.number}"

            holder.button.setOnClickListener {
                Log.d("BUTTON", "Clicked")
                val action = TablesFragmentDirections.actionTablesFragmentToOrderFragment(tableNum = table.number)
                holder.view.findNavController().navigate(action)
            }
        }
}