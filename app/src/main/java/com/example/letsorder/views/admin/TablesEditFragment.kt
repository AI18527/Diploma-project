package com.example.letsorder.views.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.adapters.TablesAdminAdapter
import com.example.letsorder.databinding.FragmentTablesEditBinding
import com.example.letsorder.viewmodel.TablesEditViewModel


class TablesEditFragment : Fragment(), TableEditListener{
    val viewModel: TablesEditViewModel by viewModels()

    private var _binding: FragmentTablesEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView : RecyclerView
    private lateinit var tableRecyclerAdapter: TablesAdminAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTablesEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerTablesList
        recyclerView.layoutManager = LinearLayoutManager(context)
        tableRecyclerAdapter = TablesAdminAdapter(viewModel, this)
        recyclerView.adapter = tableRecyclerAdapter

        _binding?.apply {
            buttonAddTable.setOnClickListener {
                viewModel.doesTableExist(binding.inputNum.text.toString().toInt())
                viewModel.addTable(binding.inputNum.text.toString().toInt(), binding.inputCapacity.text.toString().toInt())
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.removeListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun tableDeleted(table: Int) {
        viewModel.deleteTable(table)
    }
}

interface TableEditListener {
    fun tableDeleted(table: Int)
}