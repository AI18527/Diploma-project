package com.example.letsorder.views.admin

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.letsorder.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

//    private var activityResultLauncher: ActivityResultLauncher<Array<String>>
//
//    init{
//        this.activityResultLauncher = registerForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()) { result ->
//            var allAreGranted = true
//            for(b in result.values) {
//                allAreGranted = allAreGranted && b
//            }
//
//            if(allAreGranted) {
//                chooseImageGallery()
//            }
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseImageGallery()
    }

    private fun chooseImageGallery() {
        val intent = Intent(ACTION_PICK)
        intent.type = "image/*"
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                binding.imageView.setImageURI(result.data?.data)
            } else {
                Log.d("Error", "Gallery did not open")
            }
        }
        startForResult.launch(intent)
    }
}