package com.example.reconocedorrazasapp.doglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reconocedorrazasapp.model.Dog
import com.example.reconocedorrazasapp.repository.DogRepository
import kotlinx.coroutines.launch

class DogListViewModel(private val dogRepository: DogRepository): ViewModel() {

    private val _dogList = MutableLiveData<MutableList<Dog>>()
    val dogListLV: LiveData<MutableList<Dog>>
        get() = _dogList

    init {
        downloadDogsList()
    }

    private fun downloadDogsList() {
        viewModelScope.launch {
              _dogList.value = dogRepository.fetchDogs()
        }
    }
}