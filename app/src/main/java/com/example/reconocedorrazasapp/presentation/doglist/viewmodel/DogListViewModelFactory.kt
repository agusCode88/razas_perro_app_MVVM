package com.example.reconocedorrazasapp.presentation.doglist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reconocedorrazasapp.repository.DogRepository

class DogListViewModelFactory(
    private val dogRepository: DogRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DogListViewModel::class.java)) {
                return DogListViewModel(dogRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}