package com.example.reconocedorrazasapp.repository

import com.example.reconocedorrazasapp.R
import com.example.reconocedorrazasapp.data.api.connection.ApiResponseStatus
import com.example.reconocedorrazasapp.data.api.connection.DogsApi.retrofitService
import com.example.reconocedorrazasapp.data.api.dto.DogDTOMapper
import com.example.reconocedorrazasapp.data.api.connection.makeNetWorkCall
import com.example.reconocedorrazasapp.data.api.dto.AddDogToUserDTO
import com.example.reconocedorrazasapp.domain.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {
    private suspend fun fetchDogs(): ApiResponseStatus<List<Dog>> {
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

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> {
        return makeNetWorkCall {
            val dogListApiResponse = retrofitService.getUserDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDTOListToDogDomainList(dogDTOList)
        }
    }

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>>{
        return withContext(Dispatchers.IO){
            val allDogsListResponse = fetchDogs()
            val userDogListResponse = getUserDogs()

            if(allDogsListResponse is ApiResponseStatus.Error){
                allDogsListResponse
            } else if(userDogListResponse is ApiResponseStatus.Error){
                userDogListResponse
            } else if( allDogsListResponse is ApiResponseStatus.Success
                    && userDogListResponse is ApiResponseStatus.Success){
                ApiResponseStatus.Success(getCollectionListSuccess(allDogsListResponse.data,
                       userDogListResponse.data))
            } else{
                ApiResponseStatus.Error(R.string.unknow_error)
            }
        }
    }

    private fun getCollectionListSuccess(allDogsList: List<Dog>, userDogList: List<Dog>): List<Dog>{
        return allDogsList.map {
            if(userDogList.contains(it))
                it
            else
                Dog(0,it.index,"","",0.0,0.0,
                    "","","","","")
        }
    }

}