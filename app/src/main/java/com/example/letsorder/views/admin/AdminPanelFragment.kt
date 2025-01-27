package com.example.letsorder.views.admin

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.letsorder.R
import com.example.letsorder.util.RestaurantInfo
import com.example.letsorder.databinding.FragmentAdminPanelBinding
import com.example.letsorder.viewmodel.AdminViewModel
import java.time.LocalDateTime


class AdminPanelFragment : Fragment() {
    val viewModel : AdminViewModel by viewModels()

    private var _binding: FragmentAdminPanelBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getOrders(RestaurantInfo.restaurantId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminPanelBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.apply {
            buttonWaiters.setOnClickListener {  findNavController().navigate(R.id.action_adminPanelFragment_to_waiterEditFragment)}
            buttonMenu.setOnClickListener {  findNavController().navigate(R.id.action_adminPanelFragment_to_menuEditFragment)}
            buttonTables.setOnClickListener { findNavController().navigate(R.id.action_adminPanelFragment_to_tablesEditFragment)}

            viewModel.ordersNum.observe(viewLifecycleOwner){
                    ordersNum ->
                ordersStat.text = getString(R.string.orders_stats, LocalDateTime.now().month, ordersNum)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.removeListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}