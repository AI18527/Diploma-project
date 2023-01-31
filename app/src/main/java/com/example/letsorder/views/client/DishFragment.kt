package com.example.letsorder.views.client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.letsorder.data.Datasource
import com.example.letsorder.databinding.FragmentDishBinding
import com.example.letsorder.model.Dish
import com.example.letsorder.viewmodel.DishViewModel
import com.example.letsorder.views.SummaryOrderFragment
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import kotlin.properties.Delegates


class DishFragment : Fragment() {
    private var _binding: FragmentDishBinding? = null
    private val binding get() = _binding!!
    private var dishId by Delegates.notNull<Int>()

    private val viewModel: DishViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            dishId = it.get(DISH_ID) as Int
        }
        viewModel.getDish(dishId)
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

        var localDish = Dish()
        viewModel.dish.observe(viewLifecycleOwner
        ) { dish ->
            localDish = dish

            binding.title.text = dish.title
            binding.description.text = dish.description
            binding.price.text = NumberFormat.getCurrencyInstance().format(dish.price).toString()
        }

        binding?.apply {
            buttonAdd.setOnClickListener {
                if (!SummaryOrderFragment.active) {
                    Datasource().addDishToLocalOrder(localDish)
                    Snackbar.make(
                        view.findViewById(com.example.letsorder.R.id.dishFragment),
                        "${binding.title.text} has been added to your order!",
                        LENGTH_SHORT
                    ).show()
                }
                else {
                Snackbar.make(
                    view.findViewById(com.example.letsorder.R.id.dishFragment),
                    "Sorry, but you cannot add new dishes to your order",
                    LENGTH_SHORT
                ).show()
            }

                findNavController().navigate(com.example.letsorder.R.id.action_dishFragment_to_menuFragment)
                Navigation.findNavController(requireView()).popBackStack(
                    com.example.letsorder.R.id.dishFragment, true
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.removeListener()
    }

    companion object {
        const val DISH_ID = "dishId"
    }
}