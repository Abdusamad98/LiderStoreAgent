package com.example.liderstoreagent.utils

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.liderstoreagent.R
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


fun log(message : String, tag : String = "TTT"){
    Log.d(tag,message)
}


//
//fun Spinner.addItems(context: Context, data: ArrayList<String>){
//    val aa: ArrayAdapter<String> = ArrayAdapter(context, R.layout.spinner_text_view, data)
//    aa.setDropDownViewResource(R.layout.spinner_text_view)
//    adapter = aa
//}