package com.example.letsorder.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.adapters.CurrOrderAdapter
import com.example.letsorder.adapters.SummaryOrderAdapter
import com.example.letsorder.data.Datasource
import com.example.letsorder.databinding.FragmentSummaryOrderBinding
import com.example.letsorder.model.Dish
import com.example.letsorder.viewmodel.SummaryViewModel
import com.example.letsorder.viewmodel.SummaryViewModel.Companion.active
import java.text.NumberFormat

class SummaryOrderFragment : SummaryEditListener, Fragment() {

    private val viewModel: SummaryViewModel by viewModels()

    private var _binding: FragmentSummaryOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var summaryRecyclerAdapter: SummaryOrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSummaryOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerViewMenu
        recyclerView.layoutManager = LinearLayoutManager(context)

        if (active) {
            recyclerView.adapter = CurrOrderAdapter(Datasource.currOrder.dishes)

            binding?.apply {
                bill.text =
                    NumberFormat.getCurrencyInstance().format(Datasource.currOrder.bill)
                buttonOrder.visibility = View.INVISIBLE
                buttonAdd.visibility = View.VISIBLE
                buttonCall.visibility = View.VISIBLE
                buttonPay.visibility = View.VISIBLE
            }

        } else {
            summaryRecyclerAdapter = SummaryOrderAdapter(Datasource.localOrder, this)
            recyclerView.adapter = summaryRecyclerAdapter

            viewModel.sumBill()
            viewModel.bill.observe(viewLifecycleOwner) { bill ->
                binding.bill.text = NumberFormat.getCurrencyInstance().format(bill).toString()
            }

            binding?.apply {
                buttonOrder.setOnClickListener {
                    viewModel.sendOrder()
                    active = true
                    //TODO: add new view that use the data from the database
                    buttonOrder.visibility = View.INVISIBLE
                    buttonAdd.visibility = View.VISIBLE
                    buttonCall.visibility = View.VISIBLE
                    buttonPay.visibility = View.VISIBLE //*closing the app after paying
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun dishAdd(dish: Dish) {
        Log.d("ADD", "$dish")
        val updatedSummary = Datasource().addDishToLocalOrder(dish)
        summaryRecyclerAdapter.updateData(updatedSummary)
        viewModel.updateData(updatedSummary)
    }

    override fun dishSub(dish: Dish) {
        val updatedSummary = Datasource().removeDishFromLocalOrder(dish)
        summaryRecyclerAdapter.updateData(updatedSummary)
        viewModel.updateData(updatedSummary)
    }
}

interface SummaryEditListener {
    fun dishAdd(dish: Dish)
    fun dishSub(dish:Dish)
}