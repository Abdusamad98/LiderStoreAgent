package com.example.liderstoreagent.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.liderstoreagent.ui.pages.ClientsPage
import com.example.liderstoreagent.ui.pages.ProductsPage

class MainPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fm = ClientsPage()
                fm
            }
            else -> {
                val fm = ProductsPage()
                fm
            }
        }
    }



}
