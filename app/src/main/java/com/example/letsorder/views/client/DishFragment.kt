package com.example.letsorder.views.client

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.letsorder.R
import com.example.letsorder.util.LocalOrder
import com.example.letsorder.databinding.FragmentDishBinding
import com.example.letsorder.util.Event
import com.example.letsorder.viewmodel.DishViewModel
import com.example.letsorder.viewmodel.TableStatusViewModel
import com.google.android.material.snackbar.Snackbar
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

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drawable = CircularProgressDrawable(requireContext())
        drawable.centerRadius = 50f
        drawable.strokeWidth = 8f
        drawable.start()

        viewModel.dish.observe(viewLifecycleOwner) { dish ->
            binding.description.text = dish.description
            binding.price.text = NumberFormat.getCurrencyInstance().format(dish.price).toString()

            if (dish.image != null) {
                Glide.with(this)
                    .load("https://storage.googleapis.com/diploma-project-lets-order.appspot.com/${dish.image}")
                    .placeholder(drawable)
                    .into(binding.imageView)
            } else {
                binding.imageView.background =
                    resources.getDrawable(R.drawable.ic_baseline_image_24, resources.newTheme())
            }

            if (sharedViewModel.freeTable) {
                binding.buttonAdd.visibility = View.VISIBLE
            }

            binding.buttonAdd.setOnClickListener {
                LocalOrder().addDishToLocalOrder(dish)
                Snackbar.make(
                    view.findViewById(R.id.dishFragment),
                    "${dish.title} has been added to your order!",
                    1000
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