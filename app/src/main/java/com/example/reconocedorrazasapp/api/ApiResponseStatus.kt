package com.example.reconocedorrazasapp.api

import com.example.reconocedorrazasapp.model.Dog
sealed class ApiResponseStatus<T>{
    class Success<T>(val data: T): ApiResponseStatus<T>()
    class Loading<T>(): ApiResponseStatus<T>()
    class Error<T>(val errorMessageId: Int): ApiResponseStatus<T>()
}