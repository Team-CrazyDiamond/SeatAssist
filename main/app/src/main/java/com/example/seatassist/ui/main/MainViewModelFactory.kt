package com.example.seatassist.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    private val screenWidth: Float,
    private val screenHeight: Float,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                context = context
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}