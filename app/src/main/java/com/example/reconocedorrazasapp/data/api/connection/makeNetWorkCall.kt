package com.example.reconocedorrazasapp.data.api.connection

import com.example.reconocedorrazasapp.R
import com.example.reconocedorrazasapp.utils.UNAUTHORIZED_ERROR_CODE
import retrofit2.HttpException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException


suspend fun <T> makeNetWorkCall(
    call: suspend () -> T
): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO) {
        try {
            // Ejecutar la llamada de red
            ApiResponseStatus.Success(call())
        } catch (e: UnknownHostException) {
            ApiResponseStatus.Error(R.string.unknown_host_error)
        } catch (e: HttpException) {
            val errorMessage = if (e.code() == UNAUTHORIZED_ERROR_CODE) {
                R.string.wrong_user_or_password
            } else {
                R.string.unknow_error
            }
            ApiResponseStatus.Error(errorMessage)
        } catch (e: Exception) {
            val errorMessage = when (e.message) {
                "sign_up_error" -> R.string.error_sing_up
                "sign_in_error" -> R.string.error_sign_in
                "user_already_exists" -> R.string.user_already_exists
                else -> R.string.unknow_error
            }
            ApiResponseStatus.Error(errorMessage)
        }
    }
}
