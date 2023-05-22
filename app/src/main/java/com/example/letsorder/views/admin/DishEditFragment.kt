package com.example.letsorder.views.admin

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.letsorder.R
import com.example.letsorder.databinding.FragmentDishEditBinding
import com.example.letsorder.util.InputChecker
import com.example.letsorder.viewmodel.NewDishViewModel
import com.google.android.material.snackbar.Snackbar


class DishEditFragment : Fragment() {
    val viewModel: NewDishViewModel by viewModels()

    private var _binding: FragmentDishEditBinding? = null
    private val binding get() = _binding!!

    private var image : Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        image = uri
        binding.imageView.setImageURI(uri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonAddImage.setOnClickListener {
                getContent.launch("image/*")
            }
            buttonAdd.setOnClickListener {
                if (checkInput()) {
                    viewModel.addDishToMenu(
                        binding.editTitle.text.toString(),
                        binding.editCategory.text.toString(),
                        binding.editDescription.text.toString(),
                        binding.editPrice.text.toString().toDouble(),
                        image
                    )
                    findNavController().navigate(R.id.action_dishEditFragment_to_menuEditFragment)
                    Navigation.findNavController(requireView()).popBackStack(
                        R.id.dishEditFragment, true
                    )
                }
                else {
                    Snackbar.make(view, "Please fill all fields", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkInput(): Boolean {
        return InputChecker().checkInput(
            arrayListOf(binding.editTitle.text.toString(),
                binding.editCategory.text.toString(),
                binding.editDescription.text.toString(),
                binding.editPrice.text.toString()))
    }
}