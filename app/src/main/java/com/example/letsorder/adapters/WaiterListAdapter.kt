package com.example.letsorder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.model.Waiter
import com.example.letsorder.viewmodel.WaiterRegisterViewModel
import com.example.letsorder.views.admin.WaiterEditListener

class WaiterListAdapter(viewModel: WaiterRegisterViewModel, val waiterEditListener: WaiterEditListener) :
    RecyclerView.Adapter<WaiterListAdapter.WaiterListViewHolder>() {

    private val waiters = ArrayList<Waiter>()

    init {
        viewModel.waiters.observeForever {
            waiters.clear()
            waiters.addAll(it)
            notifyDataSetChanged()
        }
    }

    class WaiterListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.name)!!
        val email = view.findViewById<TextView>(R.id.email)!!
        val buttonDelete = view.findViewById<Button>(R.id.button_delete)!!
    }

    override fun getItemCount(): Int = waiters.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaiterListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_waiter_view, parent, false)
        return WaiterListViewHolder(view)
    }

    override fun onBindViewHolder(holder: WaiterListViewHolder, position: Int) {
        val waiter = waiters[position]
        holder.name.text = waiter.waiterName
        holder.email.text = waiter.email

        holder.buttonDelete.setOnClickListener {
            waiterEditListener.waiterDeleted(waiter)
        }
    }

//    fun updateData(data: List<Waiter>) {
//        waiters.clear()
//        waiters.addAll(data)
//        notifyDataSetChanged()
//    }

}