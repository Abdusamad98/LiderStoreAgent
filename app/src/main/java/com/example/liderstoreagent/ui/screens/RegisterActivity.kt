package com.example.liderstoreagent.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.liderstoreagent.R
import com.example.liderstoreagent.ui.MainActivity
import com.example.liderstoreagent.data.source.local.TokenSaver
import com.example.liderstoreagent.ui.viewmodels.LoginViewModel
import com.example.liderstoreagent.utils.log
import kotlinx.android.synthetic.main.activity_register_activty.*

class RegisterActivity : AppCompatActivity() {
    private val viewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_activty)

       /* btn_login.setOnClickListener {

            val parol = input_parol.text.toString()
            val login = input_login.text.toString()

            if (login.isEmpty()){
                input_login.error = "Loginni kiriting!"
            }
            else  if (parol.isEmpty()){
                input_parol.error = "Parolni kiriting!"
            }
            else {

                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }
        }*/
        login.setOnClickListener {
            viewModel.login("998911234567","123")
        }

        viewModel.progressLiveData.observe(this,progressObserver)
        viewModel.errorLoginLiveData.observe(this,errorLoginObserver)
        viewModel.connectionErrorLiveData.observe(this,connectionErrorObserver)
        viewModel.successLiveData.observe(this,successObserver)
    }
    private val progressObserver = Observer<Boolean>{
        if (it) {
            log("show")
        } else {
            log("hide")
        }
    }
    private val errorLoginObserver = Observer<String> {
        log("show toast")
    }
    private val connectionErrorObserver = Observer<Unit> {
        log("connection error")
    }
    private val successObserver = Observer<Unit> {
        log("Success")
    }
}