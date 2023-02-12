package com.example.letsorder.views.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.adapters.WaiterListAdapter
import com.example.letsorder.databinding.FragmentWaiterRegisterBinding
import com.example.letsorder.model.Waiter
import com.example.letsorder.viewmodel.WaiterRegisterViewModel
import com.google.android.material.snackbar.Snackbar

class WaiterEditFragment : WaiterEditListener, Fragment() {
    val viewModel : WaiterRegisterViewModel by viewModels()

    private var _binding: FragmentWaiterRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var waiterRecyclerAdapter: WaiterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWaiterRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerWaitersList
        recyclerView.layoutManager = LinearLayoutManager(context)
        waiterRecyclerAdapter = WaiterListAdapter(viewModel, this)
        recyclerView.adapter = waiterRecyclerAdapter

        _binding?.apply {
            buttonRegister.setOnClickListener {
                //Toast
                viewModel.addWaiter(binding.inputName.text.toString(), binding.inputEmail.text.toString())
                inputName.setText("")
                inputEmail.setText("")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun waiterDeleted(waiter: Waiter) {
        viewModel.deleteWaiter(waiter)
    }
}

interface WaiterEditListener {
    fun waiterDeleted(waiter: Waiter)
}