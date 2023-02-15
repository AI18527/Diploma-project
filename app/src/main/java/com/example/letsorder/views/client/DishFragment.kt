package com.example.letsorder.views.client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.letsorder.R
import com.example.letsorder.data.GlideApp
import com.example.letsorder.data.LocalOrder
import com.example.letsorder.databinding.FragmentDishBinding
import com.example.letsorder.viewmodel.DishViewModel
import com.example.letsorder.viewmodel.TableStatusViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import java.text.NumberFormat
import kotlin.properties.Delegates


class DishFragment : Fragment() {

    private var _binding: FragmentDishBinding? = null
    private val binding get() = _binding!!
    private var dishTitle by Delegates.notNull<String>()

    private val viewModel: DishViewModel by viewModels()
    private val sharedViewModel: TableStatusViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            dishTitle = it.get(DISH_TITLE) as String
        }
        viewModel.getDish(dishTitle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dish.observe(viewLifecycleOwner){
            dish ->
            binding.description.text = dish.description
            binding.price.text = NumberFormat.getCurrencyInstance().format(dish.price).toString()

            val storageRef = FirebaseStorage.getInstance().reference.child("${dish.image}")

            Log.d("TAG", "${dish.image?.let { storageRef}}")

            GlideApp.with(this)
                .load(dish.image?.let { storageRef })
                //.load("https://go359.com/wp-content/uploads/2021/07/placeholder-image.png")
                .into(binding.imageView)

            if (!sharedViewModel.freeTable) {
                binding.buttonAdd.visibility = View.INVISIBLE
            }
            binding.buttonAdd.setOnClickListener {
                LocalOrder().addDishToLocalOrder(dish)
                Snackbar.make(
                    view.findViewById(R.id.dishFragment),
                    "${dish.title} has been added to your order!",
                    LENGTH_SHORT
                ).show()

                findNavController().navigate(R.id.action_dishFragment_to_menuFragment)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.removeListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DISH_TITLE = "dishTitle"
    }
}