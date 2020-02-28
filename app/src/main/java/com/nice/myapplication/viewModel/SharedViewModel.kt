package com.nice.myapplication.viewModel

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class SharedViewModel: ViewModel(){
    val inputNumber = MutableLiveData<String>()
    }