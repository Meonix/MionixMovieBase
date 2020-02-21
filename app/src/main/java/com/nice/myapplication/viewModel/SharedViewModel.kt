package com.nice.myapplication.viewModel

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel(){
    val inputNumber = MutableLiveData<String>()
}