package com.example.letsorder.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.model.Order
import com.example.letsorder.viewmodel.TablesViewModel
import com.example.letsorder.views.waiter.TablesFragmentDirections
import java.util.Collections.addAll

class TablesAdapter(private val viewModel: TablesViewModel) :
    RecyclerView.Adapter<TablesAdapter.TablesViewHolder>() {

    private val tables = arrayListOf<Order>()

    init {
        viewModel.tables.observeForever {
            tables.clear()
            tables.addAll(it)
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
        val table = tables[position]
        holder.buttonTable.text = "Table ${table.tableNum}"

        holder.buttonTable.setOnClickListener {
            Log.d("BUTTON", "Clicked")
            val action =
                TablesFragmentDirections.actionTablesFragmentToOrderFragment(tableNum = table.tableNum)
            holder.view.findNavController().navigate(action)
        }
    }
}