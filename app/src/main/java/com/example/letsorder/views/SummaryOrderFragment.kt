package com.example.letsorder.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.adapters.SummaryOrderAdapter
import com.example.letsorder.data.Datasource
import com.example.letsorder.databinding.FragmentSummaryOrderBinding

class SummaryOrderFragment : Fragment() {

    private var _binding: FragmentSummaryOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

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
        recyclerView.adapter = SummaryOrderAdapter(Datasource().loadOrder())

        binding?.apply {
            buttonOrder.setOnClickListener {
                buttonOrder.visibility = View.INVISIBLE
                buttonAdd.visibility = View.VISIBLE
                buttonCall.visibility = View.VISIBLE
                buttonPay.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}