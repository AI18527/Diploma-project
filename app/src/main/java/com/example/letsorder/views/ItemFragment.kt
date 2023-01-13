package com.example.letsorder.views

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.letsorder.R
import com.example.letsorder.data.Datasource
import com.example.letsorder.databinding.FragmentItemBinding
import com.example.letsorder.model.Dish
import java.text.NumberFormat


class ItemFragment : Fragment() {
    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var dishId: Integer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dishId = it.get(DISHID) as Integer
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemBinding.inflate(inflater, container, false)

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
                addToOrder(dish)
                myOrder()
            }
        }
    }

    private fun addToOrder(dish:Dish){
        Datasource().addDishToOrder(dish)
        Log.d("ADD", "Order: ${Datasource().loadOrder().size}")
    }

    private fun myOrder() {
        findNavController().navigate(R.id.action_itemFragment_to_summaryOrderFragment)
    }

    private fun getDish(dishId: Integer): Dish {
        for (dish: Dish in Datasource().loadDishes()){
            if (dish.id as Integer == dishId){
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
       const val DISHID = "dishId"
    }
}