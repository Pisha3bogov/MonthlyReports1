package com.example.monthlyreport.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monthlyreport.db.Product

open class DataModel: ViewModel() {
    val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val month: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val year: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}