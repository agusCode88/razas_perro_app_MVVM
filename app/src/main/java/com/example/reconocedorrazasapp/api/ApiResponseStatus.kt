package com.example.reconocedorrazasapp.api

import com.example.reconocedorrazasapp.model.Dog

sealed class ApiResponseStatus {
    class Success(val dogList: List<Dog>): ApiResponseStatus()
    class Loading(): ApiResponseStatus()
    class Error(val errorMessageId: Int): ApiResponseStatus()
}