package com.example.letsorder.views.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsorder.adapters.WaiterListAdapter
import com.example.letsorder.databinding.FragmentWaiterEditBinding
import com.example.letsorder.model.Waiter
import com.example.letsorder.viewmodel.WaiterEditViewModel

class WaiterEditFragment : WaiterEditListener, Fragment() {
    val viewModel : WaiterEditViewModel by viewModels()

    private var _binding: FragmentWaiterEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var waiterRecyclerAdapter: WaiterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWaiterEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerWaitersList
        recyclerView.layoutManager = LinearLayoutManager(context)
        waiterRecyclerAdapter = WaiterListAdapter(WaiterEditViewModel(), this)
        recyclerView.adapter = waiterRecyclerAdapter

        _binding?.apply {
            buttonRegister.setOnClickListener {
                viewModel.addWaiter(binding.inputName.text.toString(), binding.inputEmail.text.toString())
//                auth.createUserWithEmailAndPassword(binding.inputEmail.text.toString(), "123456")
//                    .addOnCompleteListener() { task ->
//                        if (task.isSuccessful) {
//                            Log.d("TAG", "createUserWithEmail:success")
//                            //val user = auth.currentUser
//                        } else {
//                            Log.w("TAG", "createUserWithEmail:failure", task.exception)
//                        }
//                    }
                //waiterRecyclerAdapter.updateData(Datasource().loadWaiters())
                binding.inputEmail.setText("")
            }
        }
    }

//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            Log.d("User", "$currentUser")
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun waiterDeleted(waiter: Waiter) {
        //val updatedWaiters = Datasource().deleteWaiter(waiter)
        //waiterRecyclerAdapter.updateData(updatedWaiters)
    }
}

interface WaiterEditListener {
    fun waiterDeleted(waiter: Waiter)
}