package com.example.letsorder.views.waiter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.adapters.OrderAdapter
import com.example.letsorder.databinding.FragmentOrderBinding
import com.example.letsorder.model.OrderDetails
import com.example.letsorder.viewmodel.OrderViewModel
import java.text.NumberFormat
import kotlin.properties.Delegates


class OrderFragment : Fragment() {
    private val viewModel: OrderViewModel by viewModels()

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private var tableNum by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tableNum = it.get(TABLE_NUM) as Int
        }
        viewModel.getOrder(tableNum)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerViewOrder
        recyclerView.layoutManager = LinearLayoutManager(context)

        var orderList: ArrayList<OrderDetails>
        viewModel.order.observe(viewLifecycleOwner) { order ->
            orderList = order as ArrayList<OrderDetails>

            recyclerView.adapter = OrderAdapter(orderList)
        }
        viewModel.bill.observe(viewLifecycleOwner) { bill ->
            binding.orderBill.text = "Bill: ${NumberFormat.getCurrencyInstance().format(bill)}"
        }

        _binding?.apply {
            buttonDone.setOnClickListener {
                viewModel.reMoveOrder(tableNum)
                findNavController().navigate(R.id.action_orderFragment_to_tablesFragment)
                Navigation.findNavController(requireView()).popBackStack(
                    R.id.orderFragment, true
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TABLE_NUM = "tableNum"
    }
}