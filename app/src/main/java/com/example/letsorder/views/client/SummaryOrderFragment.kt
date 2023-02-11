package com.example.letsorder.views.client

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.data.LocalOrder
import com.example.letsorder.adapters.CurrOrderAdapter
import com.example.letsorder.adapters.SummaryOrderAdapter
import com.example.letsorder.databinding.FragmentSummaryOrderBinding
import com.example.letsorder.model.Dish
import com.example.letsorder.viewmodel.SummaryViewModel
import com.example.letsorder.viewmodel.TableStatusViewModel
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat

class SummaryOrderFragment : SummaryEditListener, Fragment() {

    private val viewModel: SummaryViewModel by viewModels()
    private val sharedViewModel: TableStatusViewModel by activityViewModels()

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

        //TODO: better logic
        if (sharedViewModel.freeTable) {
            summaryRecyclerAdapter =
                SummaryOrderAdapter(viewModel, LocalOrder().loadLocalOrder(), this)
            recyclerView.adapter = summaryRecyclerAdapter

            viewModel.sumBill()
            viewModel.bill.observe(viewLifecycleOwner) { bill ->
                binding.bill.text =
                    NumberFormat.getCurrencyInstance().format(bill).toString()
            }
            binding.buttonOrder.setOnClickListener {
                viewModel.sendOrder()
                sharedViewModel.takeTable()
                showButtons()
            }
        } else {
            sharedViewModel.getOrder()
            sharedViewModel.tableOrder.observe(viewLifecycleOwner) { order ->
                recyclerView.adapter = CurrOrderAdapter(order.dishes)
                binding.bill.text =
                    NumberFormat.getCurrencyInstance().format(order.bill).toString()
            }
            showButtons()
        }
    }

    private fun showButtons() {
        binding?.apply {
            buttonOrder.visibility = View.INVISIBLE
            buttonCall.visibility = View.VISIBLE
            buttonPay.visibility = View.VISIBLE

            binding.buttonCall.setOnClickListener {
                viewModel.callWaiter()
                buttonCall.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.dark_orange
                    )
                )
            }
            binding.buttonPay.setOnClickListener {
                viewModel.callWaiter()
                //snack error
                buttonCall.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.dark_orange
                    )
                )
            }
        }
    }

    /*override fun onPause() {
        super.onPause()
        viewModel.removeListener()
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun dishAdd(dish: Dish) {
        val updatedSummary = LocalOrder().addDishToLocalOrder(dish)
        summaryRecyclerAdapter.updateData(updatedSummary)
        viewModel.updateData(updatedSummary)
    }

    override fun dishSub(dish: Dish) {
        val updatedSummary = LocalOrder().removeDishFromLocalOrder(dish)
        summaryRecyclerAdapter.updateData(updatedSummary)
        viewModel.updateData(updatedSummary)
    }
}

interface SummaryEditListener {
    fun dishAdd(dish: Dish)
    fun dishSub(dish: Dish)
}