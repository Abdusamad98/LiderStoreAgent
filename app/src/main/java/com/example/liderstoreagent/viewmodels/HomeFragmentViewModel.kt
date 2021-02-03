package com.example.liderstoreagent.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class HomeFragmentViewModel:ViewModel() {
    val selectPageLiveData = MutableLiveData<Int>()
    private var currentPage = 0

          init {

          }

    fun selectPagePosition(pos: Int) {
        if (currentPage != pos) {
            selectPageLiveData.value = pos
            currentPage = pos
        }
    }
}