package com.example.liderstoreagent.ui.screens


import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.liderstoreagent.R


@Suppress("DEPRECATION")
class SplashFragment : Fragment(R.layout.splash_fragment) {
    private lateinit var mHandler: Handler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mHandler = Handler()

        mHandler.postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }, 2000)

    }

}