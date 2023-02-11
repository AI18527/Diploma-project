package com.example.letsorder.views.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.letsorder.R
import com.example.letsorder.databinding.FragmentDishEditBinding
import com.example.letsorder.viewmodel.DishEditViewModel


class DishEditFragment : Fragment() {
    val viewModel: DishEditViewModel by viewModels()

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
            buttonAdd.setOnClickListener {
                viewModel.addDishToMenu(
                    binding.editCategory.text.toString(),
                    binding.editTitle.text.toString(),
                    binding.editDescription.text.toString(),
                    binding.editPrice.text.toString().toDouble()
                )

                findNavController().navigate(R.id.action_dishEditFragment_to_menuEditFragment)
                Navigation.findNavController(requireView()).popBackStack(
                    R.id.dishEditFragment, true
                )
            }
        }
    }
}

    /*private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                binding.imageView.setImageURI(result.data?.data)
            }
            else{
                Log.d("Error", "Camera did not open")
            }
        }
        startForResult.launch(intent)
    }*/