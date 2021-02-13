package com.example.liderstoreagent.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.liderstoreagent.R
import com.example.liderstoreagent.ui.MainActivity
import com.example.liderstoreagent.data.source.local.TokenSaver

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
    private lateinit var mHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        mHandler = Handler()
        mHandler.postDelayed({
            if (TokenSaver.token.isEmpty()) {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 2000)


    }
}