package com.aa.chatapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aa.chatapp.R
import com.aa.chatapp.databinding.FragmentLoginBinding

class LoginF : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewBackToRegisterScreen.setOnClickListener {
            findNavController().navigate(R.id.action_loginF_to_registerF)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}