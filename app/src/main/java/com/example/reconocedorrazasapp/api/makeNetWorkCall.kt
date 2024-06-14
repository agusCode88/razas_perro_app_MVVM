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
            // Acá (call) es donde yo puedo enviar los corchetes que sea y lo hace genérico
            ApiResponseStatus.Success(call())
        } catch(e : UnknownHostException){
            ApiResponseStatus.Error(R.string.unknow_hoost_error)
        } catch (e: Exception){
            when(e.message){
                "sign_up_error" -> R.string.error_sing_up
                "sign_in_error" -> R.string.error_sign_in
                "user_already_exists"  -> R.string.user_already_exists
                else -> e.toString()
            }
            ApiResponseStatus.Error(R.string.unknow_error)
        }
    }
}