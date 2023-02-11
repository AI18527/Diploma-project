package com.example.letsorder.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.letsorder.R
import com.example.letsorder.databinding.FragmentLoginBinding
import com.example.letsorder.viewmodel.LoginViewModel
import com.example.letsorder.views.admin.AdminMain
import com.example.letsorder.views.waiter.WaiterMain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    val viewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.apply {
            buttonLogin.setOnClickListener {
                viewModel.checkUser(
                    binding.inputEmail.text.toString(),
                    binding.inputPassword.text.toString(), ::navigateToAdmin, ::navigateToWaiter
                )

//                if (!viewModel.isWaiter) {
//                    Log.d("TAG", "${viewModel.isWaiter}")
//                    Snackbar.make(
//                        view.findViewById(R.id.loginFragment),
//                        "You are not registered!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
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

    private fun navigateToAdmin() {
        startActivity(Intent(activity, AdminMain::class.java))
        Navigation.findNavController(requireView()).popBackStack(
            R.id.loginFragment, true
        )
    }

    private fun navigateToWaiter() {
        startActivity(Intent(activity, WaiterMain::class.java))
        Navigation.findNavController(requireView()).popBackStack(
            R.id.loginFragment, true
        )
    }
}