package com.example.letsorder.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_SHORT
import androidx.navigation.fragment.findNavController
import com.example.letsorder.R
import com.example.letsorder.data.Datasource
import com.example.letsorder.databinding.FragmentDishBinding
import com.example.letsorder.model.Dish
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import kotlin.properties.Delegates


class DishFragment : Fragment() {
    private var _binding: FragmentDishBinding? = null
    private val binding get() = _binding!!
    private var dishId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dishId = it.get(DISH_ID) as Int
        }
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

        var dish: Dish = getDish(dishId)

        binding.title.text = dish.title
        binding.description.text = dish.description
        binding.prize.text = NumberFormat.getCurrencyInstance().format(dish.prize).toString()

        binding?.apply {
            buttonAdd.setOnClickListener {
                Datasource().addDishToOrder(dish)
                Snackbar.make(
                    view.findViewById(R.id.dishFragment),
                    "${dish.title} has been added to your order!",
                    LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_dishFragment_to_menuFragment)
            }
        }
    }

    private fun getDish(dishId: Int): Dish {
        for (dish: Dish in Datasource().loadDishes()) {
            if (dish.id == dishId) {
                return dish
            }
        }
        return Dish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DISH_ID = "dishId"
    }
}