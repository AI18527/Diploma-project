package com.example.letsorder.views.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.adapters.MenuAdapter
import com.example.letsorder.databinding.FragmentMenuBinding
import com.example.letsorder.model.Dish
import com.example.letsorder.util.LocalOrder
import com.example.letsorder.viewmodel.MenuViewModel


class MenuFragment : Fragment() {
    private val viewModel: MenuViewModel by viewModels()

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerViewMenu: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewMenu = binding.recyclerViewCategory
        recyclerViewMenu.layoutManager = LinearLayoutManager(context)
        recyclerViewMenu.adapter = MenuAdapter(viewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}