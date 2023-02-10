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
import com.example.letsorder.databinding.FragmentQRBinding
import com.example.letsorder.viewmodel.TableStatusViewModel
import com.example.letsorder.views.client.ClientMain

class QRFragment : Fragment() {
    private val sharedViewModel: TableStatusViewModel by viewModels()

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

        binding.buttonMenu.setOnClickListener {
            sharedViewModel.doesTableExist(binding.restaurantId.text.toString().toInt(),
                binding.tableNumber.text.toString().toInt(),
                ::navigate
            )
        }
        binding.buttonLogin.setOnClickListener { findNavController().navigate(R.id.action_QRFragment_to_loginFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigate() {
        startActivity(Intent(activity, ClientMain::class.java))
    }
}
