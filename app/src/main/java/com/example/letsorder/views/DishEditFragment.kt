package com.example.letsorder.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.letsorder.R
import com.example.letsorder.data.Datasource
import com.example.letsorder.databinding.FragmentDishEditBinding
import com.example.letsorder.model.Dish


class DishEditFragment : Fragment() {

    private var _binding: FragmentDishEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDishEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         binding?.apply{
             buttonAdd.setOnClickListener{
                 Datasource().addDishToMenu(binding.editCategory.text.toString(),binding.editTitle.text.toString(), binding.editDescription.text.toString(), binding.editPrice.text.toString().toDouble())

                 findNavController().navigate(R.id.action_dishEditFragment_to_menuEditFragment)
                 Navigation.findNavController(requireView()).popBackStack(
                     R.id.dishEditFragment, true
                 )
             }
         }
    }

}