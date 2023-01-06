package com.example.letsorder.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.letsorder.MainActivity
import com.example.letsorder.R
import com.example.letsorder.databinding.FragmentLoginBinding
import com.example.letsorder.databinding.FragmentMenuBinding
import com.example.letsorder.databinding.FragmentQRBinding
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
            buttonLogin.setOnClickListener { goToNextScreen() }
        }
    }

    private fun goToNextScreen() {
        if (binding.inputEmail.text.toString() == "Admin") {
            startActivity(Intent(activity, AdminMain::class.java))
           // findNavController().navigate(R.id.action_loginFragment_to_adminPanelFragment)
        }
       /* else {
            //findNavController().navigate(R.id.action_loginFragment_to_tablesFragment)
            startActivity(Intent(activity, WaiterMain::class.java))
        }*/
    }

}