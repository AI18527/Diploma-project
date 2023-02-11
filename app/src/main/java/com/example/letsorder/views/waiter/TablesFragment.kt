package com.example.letsorder.views.waiter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.adapters.TablesAdapter
import com.example.letsorder.databinding.FragmentTablesBinding
import com.example.letsorder.viewmodel.TablesViewModel


class TablesFragment : Fragment() {
    private val viewModel: TablesViewModel by viewModels()

    private var _binding: FragmentTablesBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTablesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerViewTables
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = TablesAdapter(TablesViewModel(), requireContext())
    }

    override fun onPause() {
        super.onPause()
        viewModel.removeListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}