package com.example.reconocedorrazasapp.repository

import com.example.reconocedorrazasapp.api.DogsApi.retrofitService
import com.example.reconocedorrazasapp.api.responses.DogListApiResponse
import com.example.reconocedorrazasapp.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {

    suspend fun fetchDogs(): MutableList<Dog> {
        return withContext(Dispatchers.IO) {
             val dogListApiResponse = retrofitService.fetchAllDogs()
             dogListApiResponse.data.dogs
        }
    }

}