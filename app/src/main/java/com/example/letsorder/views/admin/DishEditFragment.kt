package com.example.letsorder.views.admin

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

    private var activityResultLauncher: ActivityResultLauncher<Array<String>>

    init{
        this.activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) {result ->
            var allAreGranted = true
            for(b in result.values) {
                allAreGranted = allAreGranted && b
            }

            if(allAreGranted) {
                chooseImageGallery()
            }
        }
    }

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
            buttonAddImage.setOnClickListener{
                val permissions = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                activityResultLauncher.launch(permissions)
            }

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

    private fun chooseImageGallery() {
        Log.d("TAG", "To gallery")
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    binding.imageView.setImageURI(result.data?.data)
                } else {
                    Log.d("Error", "Camera did not open")
                }
            }
        startForResult.launch(intent)
    }
}