package com.example.letsorder.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.adapters.MenuAdapter
import com.example.letsorder.adapters.OrderAdapter
import com.example.letsorder.data.Datasource
import com.example.letsorder.databinding.FragmentMenuBinding
import com.example.letsorder.databinding.FragmentOrderBinding
import com.example.letsorder.views.DishFragment.Companion.DISH_ID
import com.example.letsorder.views.OrderFragment.Companion.TABLE_NUM
import kotlin.properties.Delegates


class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private var tableNum by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tableNum = it.get(TABLE_NUM) as Int
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerViewOrder
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = OrderAdapter(Datasource().loadOrderforTable(tableNum))

        _binding?.apply {
            buttonDone.setOnClickListener { findNavController().navigate(R.id.action_orderFragment_to_tablesFragment) }
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