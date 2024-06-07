package com.example.reconocedorrazasapp.repository

import com.example.reconocedorrazasapp.R
import com.example.reconocedorrazasapp.api.ApiResponseStatus
import com.example.reconocedorrazasapp.api.DogsApi.retrofitService
import com.example.reconocedorrazasapp.api.dto.DogDTOMapper
import com.example.reconocedorrazasapp.api.makeNetWorkCall
import com.example.reconocedorrazasapp.api.responses.DogListApiResponse
import com.example.reconocedorrazasapp.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class DogRepository {
    suspend fun fetchDogs(): ApiResponseStatus<List<Dog>>{
        return makeNetWorkCall {
            val dogListApiResponse = retrofitService.fetchAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDTOListToDogDomainList(dogDTOList)
        }
    }
}