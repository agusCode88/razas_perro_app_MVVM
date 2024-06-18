package com.example.reconocedorrazasapp.presentation.doglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reconocedorrazasapp.data.api.connection.ApiResponseStatus
import com.example.reconocedorrazasapp.domain.model.Dog
import com.example.reconocedorrazasapp.repository.DogRepository
import kotlinx.coroutines.launch

class DogListViewModel(private val dogRepository: DogRepository): ViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogListLV: LiveData<List<Dog>>
        get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus<List<Dog>>>()
    val status: LiveData<ApiResponseStatus<List<Dog>>>
        get() = _status

    init {
        downloadDogsList()
    }

    private fun downloadDogsList() {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.fetchDogs())
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if(apiResponseStatus is ApiResponseStatus.Success){
            _dogList.value = apiResponseStatus.data
        }
        _status.value = apiResponseStatus
    }
}