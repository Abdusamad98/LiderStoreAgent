package com.example.liderstoreagent.ui.pages

import androidx.fragment.app.Fragment
import com.example.liderstoreagent.R

class ClientsPage : Fragment(R.layout.clients_fragment) {
    private var countListener : ((Int) -> Unit)? = null
    fun click(f : (Int) ->Unit) {
        countListener = f
    }
}