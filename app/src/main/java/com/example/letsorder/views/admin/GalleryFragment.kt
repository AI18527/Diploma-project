package com.example.letsorder.views.admin

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.letsorder.databinding.FragmentGalleryBinding
import com.example.letsorder.viewmodel.NewDishViewModel
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class GalleryFragment : Fragment() {
    val viewModel: NewDishViewModel by viewModels()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    var image: Uri? = null

    private var dishTitle by Delegates.notNull<String>()
    private var dishCategory by Delegates.notNull<String>()
    private var dishDescription by Delegates.notNull<String>()
    private var dishPrice by Delegates.notNull<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            dishTitle = it.get(DISH_TITLE) as String
            dishCategory = it.get(DISH_CATEGORY) as String
            dishDescription = it.get(DISH_DESCRIPTION) as String
            dishPrice = it.get(DISH_PRICE) as String
        }
    }

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

        binding.title.text = dishTitle
        binding.category.text = dishCategory
        binding.description.text = dishDescription
        binding.price.text = dishPrice

        binding.buttonAdd.setOnClickListener {
            if (image != null) {
                viewModel.addDishWithPick(
                    dishTitle,
                    dishCategory,
                    dishDescription,
                    dishPrice.toDouble(),
                    image!!
                )
                findNavController().navigate(GalleryFragmentDirections.actionGalleryFragmentToMenuEditFragment())
            }
            else {
                Snackbar.make(view, "There is no image", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun chooseImageGallery() {
        val intent = Intent(ACTION_PICK)
        intent.type = "image/*"
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    image = result.data?.data
                    binding.imageView.setImageURI(result.data?.data)
                } else {
                    Log.d("Error", "Gallery did not open")
                }
            }
        startForResult.launch(intent)
    }
    companion object {
        const val DISH_TITLE = "dishTitle"
        const val DISH_CATEGORY = "dishCategory"
        const val DISH_DESCRIPTION = "dishDescription"
        const val DISH_PRICE = "dishPrice"
    }
}