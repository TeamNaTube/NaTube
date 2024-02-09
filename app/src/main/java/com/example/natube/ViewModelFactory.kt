package com.example.natube

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.natube.ui.home.HomeRepository
import com.example.natube.ui.home.HomeViewModel

class ViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}