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
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.letsorder.R
import com.example.letsorder.data.Event
import com.example.letsorder.databinding.FragmentQRBinding
import com.example.letsorder.model.Order
import com.example.letsorder.viewmodel.TableStatusViewModel
import com.example.letsorder.viewmodel.TableStatusViewModel.Companion.FREETABLE
import com.example.letsorder.views.client.ClientMain
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class QRFragment : Fragment() {
    private val sharedViewModel: TableStatusViewModel by viewModels()

    private var _binding: FragmentQRBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQRBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //startCamera()
        /*val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                //val intent = result.data
                val imageAnalyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, ImageAnalyzer())
                    }
            }
            else{
                Log.d("Error", "Camera did not open")
            }
        }
        startForResult.launch(camera)
    }*/
        binding.buttonMenu.setOnClickListener {
            sharedViewModel.doesTableExist(
                binding.restaurantId.text.toString().toInt(),
                binding.tableNumber.text.toString().toInt()
            )
            sharedViewModel.isFree.observe(viewLifecycleOwner) { data: Event<Boolean> ->
                val d = data.handle()
                d?.let {
                    if (d) {
                        navigate()
                    } else
                        FREETABLE = false
                    navigate()

                }
            }
        }
        binding.buttonLogin.setOnClickListener { findNavController().navigate(R.id.action_QRFragment_to_loginFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigate() {
        startActivity(Intent(activity, ClientMain::class.java))
        sharedViewModel.removeListeners()
    }

    /* private fun startCamera() {
         val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

         cameraProviderFuture.addListener({
             val cameraProvider = cameraProviderFuture.get()

             // Preview
             val preview = Preview.Builder()
                 .build()
                 .also {
                     it.setSurfaceProvider(binding.previewView.createSurfaceProvider())
                 }

             // Image analyzer
             val imageAnalyzer = ImageAnalysis.Builder()
                 .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                 .build()
                 .also {
                     it.setAnalyzer(cameraExecutor, ImageAnalyzer())
                 }

             // Select back camera as a default
             val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

             try {
                 // Unbind use cases before rebinding
                 cameraProvider.unbindAll()

                 // Bind use cases to camera
                 cameraProvider.bindToLifecycle(
                     this, cameraSelector, preview, imageAnalyzer
                 )

             } catch (exc: Exception) {
                 exc.printStackTrace()
             }
         }, ContextCompat.getMainExecutor(requireContext()))
     }*/
}
