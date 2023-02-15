package com.example.letsorder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.model.Table
import com.example.letsorder.viewmodel.TablesEditViewModel
import com.example.letsorder.views.admin.TablesEditFragment

class TablesAdminAdapter(viewModel: TablesEditViewModel, val tableEditListener: TablesEditFragment) :
    RecyclerView.Adapter<TablesAdminAdapter.TablesAdminViewHolder>() {

    private val tables = ArrayList<Table>()

    init {
        viewModel.tables.observeForever {
            tables.clear()
            tables.addAll(it)
            notifyDataSetChanged()
        }
    }

    class TablesAdminViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tableNum = view.findViewById<TextView>(R.id.tableNumber)!!
        val capacity = view.findViewById<TextView>(R.id.capacity)!!
        val buttonDelete = view.findViewById<Button>(R.id.buttonDelete)!!
    }

    override fun getItemCount(): Int = tables.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablesAdminViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_admin_table_view, parent, false)
        return TablesAdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: TablesAdminViewHolder, position: Int) {
        val table = tables[position]
        holder.tableNum.text = "Table " + table.tableNum.toString()
        holder.capacity.text = "Capacity: " + table.capacity.toString()

        holder.buttonDelete.setOnClickListener {
            tableEditListener.tableDeleted(table.tableNum)
        }
    }
}