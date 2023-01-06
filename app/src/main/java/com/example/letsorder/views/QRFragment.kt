package com.example.letsorder.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.letsorder.R
import com.example.letsorder.databinding.FragmentQRBinding

class QRFragment : Fragment() {

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
            buttonMenu.setOnClickListener { menu() }
            buttonLogin.setOnClickListener { login() }
        }
    }

    private fun login() {
        findNavController().navigate(R.id.action_QRFragment_to_loginFragment)
    }

    private fun menu() {
        startActivity(Intent(activity, ClientMain::class.java))
        //findNavController().navigate(R.id.action_QRFragment_to_menuFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
