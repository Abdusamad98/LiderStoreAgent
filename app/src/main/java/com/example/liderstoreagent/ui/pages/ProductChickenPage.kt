package com.example.liderstoreagent.ui.pages

import androidx.fragment.app.Fragment
import com.example.liderstoreagent.R

class ProductChickenPage : Fragment(R.layout.product_chicken_fragment) {
    private var countListener : ((Int) -> Unit)? = null
    fun click(f : (Int) ->Unit) {
        countListener = f
    }
}