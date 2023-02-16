package com.example.letsorder.views.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.letsorder.R
import com.example.letsorder.databinding.FragmentDishEditBinding
import com.example.letsorder.viewmodel.NewDishViewModel
import com.google.android.material.snackbar.Snackbar


class DishEditFragment : Fragment() {
    val viewModel: NewDishViewModel by viewModels()

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

        binding?.apply {
            buttonAddImage.setOnClickListener {
                if (checkInput()) {
                    findNavController().navigate(
                        DishEditFragmentDirections.actionDishEditFragmentToGalleryFragment(
                            dishTitle = binding.editTitle.text.toString(),
                            dishCategory = binding.editCategory.text.toString(),
                            dishDescription = binding.editDescription.text.toString(),
                            dishPrice = binding.editPrice.text.toString()
                        )
                    )
                }
                else {
                    Snackbar.make(view, "Please fill all fields", Snackbar.LENGTH_LONG).show()
                }
            }

            buttonAdd.setOnClickListener {
                if (checkInput()) {
                    viewModel.addDishToMenu(
                        binding.editCategory.text.toString(),
                        binding.editTitle.text.toString(),
                        binding.editDescription.text.toString(),
                        binding.editPrice.text.toString().toDouble()
                    )
                    findNavController().navigate(R.id.action_dishEditFragment_to_menuEditFragment)
                }
                else {
                    Snackbar.make(view, "Please fill all fields", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    fun checkInput(): Boolean {
        if (binding.editTitle.text.toString() == "" || binding.editPrice.text.toString() == "" || binding.editCategory.text.toString() == "" || binding.editDescription.text.toString() == ""){
            return false
        }
        return true
    }
}