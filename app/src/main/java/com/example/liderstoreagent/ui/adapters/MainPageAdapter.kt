package com.example.liderstoreagent.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.liderstoreagent.ui.pages.ClientsPage
import com.example.liderstoreagent.ui.pages.DiscountsPage
import com.example.liderstoreagent.ui.pages.MarketPage
import com.example.liderstoreagent.ui.pages.ProductsPage

class MainPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var eventListener: ((Int) -> Unit)? = null
    val marketPage = MarketPage()

    override fun getCount(): Int = 4

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fm = ClientsPage()
                fm
            }
            1 -> {
                val fm = ProductsPage()
                fm.eventListener { id ->
                    eventListener?.invoke(id)
                }
                fm
            }
            2 -> {
                val fm = DiscountsPage()
                fm.eventDiscountListener { id ->
                    eventListener?.invoke(id)
                }
                fm
            }
            3 -> {
                marketPage
            }
            else -> {
                val fm = ClientsPage()
                fm
            }
        }
    }

    fun eventListener(f: (Int) -> Unit) {
        eventListener = f
    }

}
