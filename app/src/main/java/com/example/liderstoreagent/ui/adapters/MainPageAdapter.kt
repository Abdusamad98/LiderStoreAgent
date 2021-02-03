package com.example.liderstoreagent.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.liderstoreagent.ui.pages.ClientsPage
import com.example.liderstoreagent.ui.pages.ProductChickenPage
import com.example.liderstoreagent.ui.pages.ProductMilkPage
import com.example.liderstoreagent.ui.pages.ProductSausagePage

class MainPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = 4

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fm = ClientsPage()
                fm
            }
            1 -> {
                val fm = ProductChickenPage()
                fm
            }
            2 -> {
                val fm = ProductSausagePage()
                fm
            }
            else -> {
                val fm = ProductMilkPage()
                fm
            }
        }
    }



}
