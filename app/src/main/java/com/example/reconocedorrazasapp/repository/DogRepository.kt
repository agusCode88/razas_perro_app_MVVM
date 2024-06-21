package com.example.reconocedorrazasapp.repository

import com.example.reconocedorrazasapp.data.api.connection.ApiResponseStatus
import com.example.reconocedorrazasapp.data.api.connection.DogsApi.retrofitService
import com.example.reconocedorrazasapp.data.api.dto.DogDTOMapper
import com.example.reconocedorrazasapp.data.api.connection.makeNetWorkCall
import com.example.reconocedorrazasapp.data.api.dto.AddDogToUserDTO
import com.example.reconocedorrazasapp.domain.model.Dog

class DogRepository {
    suspend fun fetchDogs(): ApiResponseStatus<List<Dog>> {
        return makeNetWorkCall {
            val dogListApiResponse = retrofitService.fetchAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDTOListToDogDomainList(dogDTOList)
        }
    }

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> = makeNetWorkCall {
        val addDogToUserDTO = AddDogToUserDTO(dogId)
        val defaultReponse = retrofitService.addDogToUser(addDogToUserDTO)

        if(!defaultReponse.isSuccess){
            throw Exception(defaultReponse.message)
        }
    }
}