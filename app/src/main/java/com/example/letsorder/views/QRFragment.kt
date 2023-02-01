package com.example.letsorder.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.letsorder.R
import com.example.letsorder.data.Datasource.Companion.free
import com.example.letsorder.data.Datasource.Companion.tableNum
import com.example.letsorder.databinding.FragmentQRBinding
import com.example.letsorder.viewmodel.QRViewModel
import com.example.letsorder.viewmodel.SummaryViewModel
import com.example.letsorder.views.client.ClientMain

class QRFragment : Fragment() {
    val viewModel : QRViewModel by viewModels()

    private var _binding: FragmentQRBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQRBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.apply {
            buttonMenu.setOnClickListener {
                val tableNumber = binding.tableNumber.text.toString().toInt()
                viewModel.isTableFree(tableNumber)
                viewModel.freeTable.observe(viewLifecycleOwner) { freeTable ->
                    free = freeTable
                    SummaryViewModel.active = !freeTable
                    if (free){
                        tableNum = tableNumber
                    }
                    startActivity(Intent(activity, ClientMain::class.java))
                }
            }
            buttonLogin.setOnClickListener { findNavController().navigate(R.id.action_QRFragment_to_loginFragment) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
