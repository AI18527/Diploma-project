package com.example.letsorder.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.adapters.SummaryOrderAdapter
import com.example.letsorder.data.Datasource
import com.example.letsorder.databinding.FragmentSummaryOrderBinding
import com.example.letsorder.model.Dish
import com.example.letsorder.viewmodel.SummaryViewModel
import java.text.NumberFormat

class SummaryOrderFragment : SummaryEditListener, Fragment() {

    private var _binding: FragmentSummaryOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var summaryRecyclerAdapter: SummaryOrderAdapter

    private val viewModel: SummaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSummaryOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.sumBill()
        viewModel.bill.observe(viewLifecycleOwner) {
            bill->
            binding.bill.text =  NumberFormat.getCurrencyInstance().format(bill).toString()
        }

        recyclerView = binding.recyclerViewMenu
        recyclerView.layoutManager = LinearLayoutManager(context)
        summaryRecyclerAdapter = SummaryOrderAdapter(Datasource().loadLocalOrder(),  this)
        recyclerView.adapter = summaryRecyclerAdapter

        binding?.apply {
            buttonOrder.setOnClickListener {
                Datasource().sendOrder()
                //TODO: add new view that use the data from the database
                buttonOrder.visibility = View.INVISIBLE
                buttonAdd.visibility = View.VISIBLE
                buttonCall.visibility = View.VISIBLE
                buttonPay.visibility = View.VISIBLE //*closing the app after paying
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun dishAdd(dish: Dish) {
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