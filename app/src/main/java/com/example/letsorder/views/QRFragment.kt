package com.example.letsorder.views

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.letsorder.R
import com.example.letsorder.util.Event
import com.example.letsorder.util.RestaurantInfo
import com.example.letsorder.databinding.FragmentQRBinding
import com.example.letsorder.viewmodel.TableStatusViewModel
import com.example.letsorder.views.client.ClientMain
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import org.json.JSONObject


class QRFragment : Fragment() {
    private val viewModel: TableStatusViewModel by viewModels()

    private var _binding: FragmentQRBinding? = null
    private val binding get() = _binding!!

    private var pick: Bitmap? = null

    private var json: JSONObject? = null

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE
        )
        .build()

    private val getPick =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            pick = bitmap
            val qr = InputImage.fromBitmap(pick!!, 0)
            val scanner = BarcodeScanning.getClient(options)
            scanner.process(qr)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isEmpty()) {
                        view?.let {
                            Snackbar.make(
                                it,
                                "There was a problem with the QR code. Please enter the information here instead.",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                    for (barcode in barcodes) {
                        when (barcode.valueType) {
                            Barcode.TYPE_TEXT -> {
                                json = JSONObject(barcode.rawValue.toString())
                                when (json!!.getString("user")) {
                                    "client" -> {
                                        viewModel.getTable(
                                            json!!.getInt("restaurantId"),
                                            json!!.getInt("tableNum")
                                        )
                                        view?.let { client(it) }
                                    }
                                    "worker" -> {
                                        RestaurantInfo.restaurantId = json!!.getInt("restaurantId")
                                        findNavController().navigate(R.id.action_QRFragment_to_loginFragment)
                                    }
                                }

                            }
                        }
                    }
                }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQRBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonScan.setOnClickListener {
            getPick.launch()
        }

        binding.buttonMenu.setOnClickListener {
            if (binding.restaurantId.text.toString() != "" &&
                binding.tableNumber.text.toString() != ""
            ) {
                viewModel.getTable(
                    binding.restaurantId.text.toString().toInt(),
                    binding.tableNumber.text.toString().toInt()
                )
                client(view)
            } else {
                Snackbar.make(
                    view,
                    "You should enter table's number and restaurant's number",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        binding.login.setOnClickListener {
            if (binding.restaurantId.text.toString() != "") {
                RestaurantInfo.restaurantId = binding.restaurantId.text.toString().toInt()
                findNavController().navigate(R.id.action_QRFragment_to_loginFragment)
            } else {
                Snackbar.make(
                    view,
                    "You should enter restaurant's number",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun client(view: View) {
        viewModel.tableExists.observe(viewLifecycleOwner) { tableExists ->
            val exists = tableExists.handle()
            exists?.let {
                if (exists) {
                    viewModel.isTableFree()
                    viewModel.isFree.observe(viewLifecycleOwner) { isFree: Event<Boolean> ->
                        val free = isFree.handle()
                        free?.let {
                            TableStatusViewModel.FREE_TABLE = free
                            navigate()
                        }
                    }
                } else {
                    Snackbar.make(
                        view,
                        "Wrong input. Check and enter again",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.removeListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigate() {
        startActivity(Intent(activity, ClientMain::class.java))
        viewModel.removeListeners()
    }
}