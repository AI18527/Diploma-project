package com.example.letsorder.views.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.letsorder.databinding.FragmentTablesEditBinding
import com.example.letsorder.viewmodel.TablesEditViewModel


class TablesEditFragment : Fragment() {
    val viewModel: TablesEditViewModel by viewModels()

    private var _binding: FragmentTablesEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTablesEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.apply {
            buttonAddTable.setOnClickListener {
                viewModel.addTable(
                    binding.inputNum.text.toString().toInt(),
                    binding.inputCapacity.text.toString().toInt()
                )
            }
        }
    }
}