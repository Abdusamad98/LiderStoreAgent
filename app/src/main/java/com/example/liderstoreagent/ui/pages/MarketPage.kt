package com.example.liderstoreagent.ui.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.liderstoreagent.R
import com.example.liderstoreagent.utils.showToast


class MarketPage :Fragment(R.layout.product_sell_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun handlerEvent(id : Int) {
        requireContext().showToast("marker screen id = $id")
    }
}