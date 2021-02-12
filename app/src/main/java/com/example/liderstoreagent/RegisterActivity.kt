package com.example.liderstoreagent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.liderstoreagent.utils.TokenSaver
import kotlinx.android.synthetic.main.activity_register_activty.*
import kotlin.math.log

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_activty)

        btn_login.setOnClickListener {

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
                TokenSaver.setToken(login + parol)

            }
        }
    }
}