package com.example.reconocedorrazasapp.repository

import com.example.reconocedorrazasapp.R
import com.example.reconocedorrazasapp.api.ApiResponseStatus
import com.example.reconocedorrazasapp.api.DogsApi.retrofitService
import com.example.reconocedorrazasapp.api.dto.DogDTOMapper
import com.example.reconocedorrazasapp.api.responses.DogListApiResponse
import com.example.reconocedorrazasapp.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class DogRepository {
    suspend fun fetchDogs(): ApiResponseStatus{
        return withContext(Dispatchers.IO) {
            try {
                val dogListApiResponse = retrofitService.fetchAllDogs()
                val dogDTOList = dogListApiResponse.data.dogs
                val dogDTOMapper = DogDTOMapper()
                ApiResponseStatus.Success(dogDTOMapper.fromDTOListToDogDomainList(dogDTOList))
            } catch(e : UnknownHostException){
                ApiResponseStatus.Error(R.string.unknow_hoost_error)
            } catch (e: Exception){
                ApiResponseStatus.Error(R.string.unknow_error)
            }
        }
    }
}