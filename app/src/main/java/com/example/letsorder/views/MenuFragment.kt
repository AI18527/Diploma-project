package com.example.letsorder.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.adapters.MenuAdapter
import com.example.letsorder.R
import com.example.letsorder.data.Datasource
import com.example.letsorder.databinding.FragmentMenuBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = binding.recyclerViewMenu
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MenuAdapter(Datasource().loadDishes())

        _binding?.apply {
            buttonMyOrder.setOnClickListener { findNavController().navigate(R.id.action_menuFragment_to_summaryOrderFragment) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}