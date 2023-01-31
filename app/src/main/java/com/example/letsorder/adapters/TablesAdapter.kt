package com.example.letsorder.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.viewmodel.TablesViewModel
import com.example.letsorder.views.waiter.TablesFragmentDirections

class TablesAdapter(viewModel: TablesViewModel, val context: Context) :
    RecyclerView.Adapter<TablesAdapter.TablesViewHolder>() {

    private val tables = mutableMapOf<Int, Boolean>()

    init {
        viewModel.tables.observeForever {
            tables.clear()
            tables.putAll(it)
            notifyDataSetChanged()
        }
    }

    class TablesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val buttonTable = view.findViewById<Button>(R.id.buttonTable)
    }

    override fun getItemCount(): Int = tables.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TablesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_table_view, parent, false)
        return TablesViewHolder(view)
    }

    override fun onBindViewHolder(holder: TablesViewHolder, position: Int) {
        val table = tables.toList()[position]

        if (!table.second) {
            holder.buttonTable.setBackgroundColor(getColor(context, R.color.green))
        }
        holder.buttonTable.text = "Table ${table.first}"

        holder.buttonTable.setOnClickListener {
            //TODO: move to the private orders// change in the base
            val action =
                TablesFragmentDirections.actionTablesFragmentToOrderFragment(tableNum = table.first)
            holder.view.findNavController().navigate(action)
        }
    }
}