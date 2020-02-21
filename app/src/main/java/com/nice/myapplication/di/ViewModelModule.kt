package com.nice.myapplication.di

import com.nice.myapplication.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()
        ,get()
        ,get()
        ,get()
        ,get()) }
}