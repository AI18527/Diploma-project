package com.example.letsorder.views.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.letsorder.R
import com.example.letsorder.databinding.FragmentAdminPanelBinding


class AdminPanelFragment : Fragment() {
    private var _binding: FragmentAdminPanelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminPanelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.apply {
            buttonWaiters.setOnClickListener { waiterEdit() }
            buttonMenu.setOnClickListener { menuEdit() }
            buttonTables.setOnClickListener { tablesEdit() }
        }
    }

    private fun menuEdit() {
        findNavController().navigate(R.id.action_adminPanelFragment_to_menuEditFragment)
    }

    private fun waiterEdit() {
        findNavController().navigate(R.id.action_adminPanelFragment_to_waiterEditFragment)
    }

    private fun tablesEdit(){
        findNavController().navigate(R.id.action_adminPanelFragment_to_tablesEditFragment)
    }

}