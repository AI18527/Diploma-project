package com.example.letsorder.views.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.adapters.MenuAdminAdapter
import com.example.letsorder.databinding.FragmentMenuEditBinding
import com.example.letsorder.viewmodel.MenuViewModel


class MenuEditFragment : Fragment(), DishEditListener {
    val viewModel: MenuViewModel by viewModels()

    private var _binding: FragmentMenuEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var menuRecyclerAdapter: MenuAdminAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerViewMenu
        recyclerView.layoutManager = LinearLayoutManager(context)
        menuRecyclerAdapter = MenuAdminAdapter(viewModel, this)
        recyclerView.adapter = menuRecyclerAdapter

        _binding?.apply {
            buttonAddDish.setOnClickListener { findNavController().navigate(R.id.action_menuEditFragment_to_dishEditFragment) }
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

    override fun dishDeleted(dish: String) {
        viewModel.deleteDish(dish)
    }
}

interface DishEditListener {
    fun dishDeleted(dish: String)
}