package com.example.letsorder.views

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.letsorder.databinding.FragmentQRBinding
import com.example.letsorder.viewmodel.TableStatusViewModel
import com.example.letsorder.views.client.ClientMain


class QRFragment : Fragment() {
    private val sharedViewModel: TableStatusViewModel by viewModels()

    private var _binding: FragmentQRBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQRBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
            }
            else{
                Log.d("Error", "Camera did not open")
            }
        }
        startForResult.launch(camera)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigate() {
        startActivity(Intent(activity, ClientMain::class.java))
    }
}
