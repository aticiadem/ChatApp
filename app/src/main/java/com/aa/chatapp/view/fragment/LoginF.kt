package com.aa.chatapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.aa.chatapp.R
import com.aa.chatapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginF : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        val view = binding.root

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.textViewBackToRegisterScreen.setOnClickListener {
            findNavController().navigate(R.id.action_loginF_to_registerF)
        }

        binding.buttonLogIn.setOnClickListener {
            if (isValidInput()){
                val email = binding.editTextEmail.text.toString()
                val password = binding.editTextPassword.text.toString()

                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            val currentUserName = auth.currentUser!!.displayName
                            Toast.makeText(requireContext(),"Hoş Geldiniz $currentUserName",Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_loginF_to_homeF)
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(requireContext(),exception.localizedMessage,Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(requireContext(),"Lütfen Bütün Alanları Doldurun!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun isValidInput(): Boolean = binding.editTextEmail.text.isNotEmpty()
            && binding.editTextPassword.text.isNotEmpty()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}