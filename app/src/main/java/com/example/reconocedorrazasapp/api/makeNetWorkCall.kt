package com.example.reconocedorrazasapp.api

import com.example.reconocedorrazasapp.R
import com.example.reconocedorrazasapp.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeNetWorkCall(
    call: suspend () -> T
): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResponseStatus.Success(call())
        } catch(e : UnknownHostException){
            ApiResponseStatus.Error(R.string.unknow_hoost_error)
        } catch (e: Exception){
            ApiResponseStatus.Error(R.string.unknow_error)
        }
    }
}