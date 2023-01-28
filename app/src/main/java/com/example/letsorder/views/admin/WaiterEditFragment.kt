package com.example.letsorder.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.R
import com.example.letsorder.adapters.MenuAdapter
import com.example.letsorder.adapters.WaiterListAdapter
import com.example.letsorder.data.Datasource
import com.example.letsorder.databinding.FragmentMenuBinding
import com.example.letsorder.databinding.FragmentWaiterEditBinding
import com.example.letsorder.model.Waiter

class WaiterEditFragment :WaiterEditListener, Fragment() {
    private var _binding: FragmentWaiterEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var waiterRecyclerAdapter: WaiterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWaiterEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerWaitersList
        recyclerView.layoutManager = LinearLayoutManager(context)
        waiterRecyclerAdapter = WaiterListAdapter(Datasource().loadWaiters(), this)
        recyclerView.adapter = waiterRecyclerAdapter

        _binding?.apply {
            buttonRegister.setOnClickListener {
                Datasource().addWaiter(
                    Waiter(
                        binding.inputEmail.text.toString(),
                        "1234"
                    )
                )
                waiterRecyclerAdapter.updateData(Datasource().loadWaiters())
                binding.inputEmail.setText("")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun waiterDeleted(waiter: Waiter) {
        val updatedWaiters = Datasource().deleteWaiter(waiter)
        waiterRecyclerAdapter.updateData(updatedWaiters)
    }
}

interface WaiterEditListener {
    fun waiterDeleted(waiter: Waiter)
}