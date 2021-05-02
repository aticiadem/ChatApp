package com.aa.chatapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aa.chatapp.R
import com.aa.chatapp.databinding.FragmentRegisterBinding

class RegisterF : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerF_to_loginF)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}