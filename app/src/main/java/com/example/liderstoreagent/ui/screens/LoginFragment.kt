package com.example.liderstoreagent.ui.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.liderstoreagent.R
import com.example.liderstoreagent.data.models.loginmodel.LoginResponse
import com.example.liderstoreagent.data.source.local.TokenSaver
import com.example.liderstoreagent.ui.viewmodels.LoginViewModel
import com.example.liderstoreagent.utils.log
import com.example.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.products_fragment.*

class LoginFragment : Fragment(R.layout.login_fragment) {
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!TokenSaver.getLogin()!!.isEmpty()){
            input_login.setText(TokenSaver.getLogin())
        }

        login.setOnClickListener {

            val password = input_parol.text.toString()
            val username = input_login.text.toString()

            if (username.isEmpty()) {
                input_login.error = "Loginni kiriting!"
            } else if (password.isEmpty()) {
                input_parol.error = "Parolni kiriting!"
            } else if (TokenSaver.token.isEmpty()) {
                viewModel.login(username, password)
            }
            else if(TokenSaver.getPassword() == input_parol.text.toString() ) {
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            }

            else {
                requireActivity().showToast("Parol xato!")
            }

        }
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        viewModel.errorLoginLiveData.observe(viewLifecycleOwner, errorLoginObserver)
        viewModel.connectionErrorLiveData.observe(viewLifecycleOwner, connectionErrorObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner, successObserver)
    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            loginProgressBar.visibility =View.VISIBLE
        } else {
            loginProgressBar.visibility =View.GONE
        }
    }

    private val errorLoginObserver = Observer<String> {
        requireActivity().showToast("Parol yoki Login xato!")
    }
    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Internet yuq!")
        log("connection error")
    }

    private val successObserver = Observer<LoginResponse> {
        AlertDialog.Builder(requireContext())
            .setTitle("Diqqat!")
            .setMessage("Tizimga kirdingiz !")
            .setPositiveButton("Ok") { dialog, _ ->
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                dialog.cancel()
            }.show()

        log("Success")
    }
}