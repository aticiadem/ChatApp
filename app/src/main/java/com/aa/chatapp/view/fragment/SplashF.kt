package com.aa.chatapp.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aa.chatapp.R
import com.aa.chatapp.databinding.FragmentSplashBinding

class SplashF : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val DELAY_TIME = 1500L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.lottie.setAnimation("animation.json")
        binding.lottie.playAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            // Go to Register Fragment
            findNavController().navigate(R.id.action_splashF_to_registerF)
        },DELAY_TIME)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}