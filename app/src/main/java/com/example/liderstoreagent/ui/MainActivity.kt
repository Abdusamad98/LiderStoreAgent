package com.example.liderstoreagent.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.example.liderstoreagent.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var graph: NavGraph
    private lateinit var host: NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        host = myNavHostFragment as NavHostFragment
        graph = host.navController.navInflater.inflate(R.navigation.navigation)

        graph.startDestination = R.id.mainFragment
        host.navController.graph = graph

    }
}