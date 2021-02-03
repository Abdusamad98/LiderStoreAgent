package com.example.liderstoreagent.utils

import android.content.Context
import android.widget.Toast
import androidx.viewpager.widget.ViewPager

fun Context.showToast(st: String) {
    Toast.makeText(this, st, Toast.LENGTH_SHORT).show()
}

fun ViewPager.pageChangeListener(f: (Int) -> Unit) =
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            f.invoke(position)
        }
    })