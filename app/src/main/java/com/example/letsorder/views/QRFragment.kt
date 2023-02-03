package com.example.letsorder.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
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
import com.google.android.material.snackbar.Snackbar

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

            /*buttonMenu.setOnClickListener {
                viewModel.isTableFree(binding.tableNumber.text.toString().toInt())

                viewModel.freeTable.observe(viewLifecycleOwner) { freeTable ->
                    //to tell you that the table is not free *pop up if you want to see the order
                    SummaryViewModel.active = !freeTable
                    Log.d("TABLE FREE", "$freeTable")
                    startActivity(Intent(activity, ClientMain::class.java))
                }
            }*/


        binding.buttonMenu.setOnClickListener {
            viewModel.isTableFree(binding.tableNumber.text.toString().toInt(), ::navigateToTableSummary)
            //startActivity(Intent(activity, ClientMain::class.java))
            /**/
        }
            //free = freeTable
            //SummaryViewModel.active = !freeTable
            //TODO: навигацията много по- бързо се изпълнява отколкото взимаменето на информация
        binding.buttonLogin.setOnClickListener { findNavController().navigate(R.id.action_QRFragment_to_loginFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToTableSummary() {
        startActivity(Intent(activity, ClientMain::class.java))
    }
}
