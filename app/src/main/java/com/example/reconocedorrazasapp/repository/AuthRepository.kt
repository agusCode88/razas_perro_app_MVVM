package com.example.reconocedorrazasapp.repository

import com.example.reconocedorrazasapp.api.ApiResponseStatus
import com.example.reconocedorrazasapp.api.DogsApi
import com.example.reconocedorrazasapp.api.dto.DogDTOMapper
import com.example.reconocedorrazasapp.api.dto.SignUpDTO
import com.example.reconocedorrazasapp.api.dto.UserDTOMapper
import com.example.reconocedorrazasapp.api.makeNetWorkCall
import com.example.reconocedorrazasapp.model.User

class AuthRepository {

    /**
     * Los DTOs se utilizan para poder enviar y recibir datos a través del servidor
     */
    suspend fun signUp(email: String, password: String, passwordConfirmation: String): ApiResponseStatus<User> {
        return makeNetWorkCall {
            val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
            val signUpResponse = DogsApi.retrofitService.signUp(signUpDTO)

            if (!signUpResponse.isSuccess ) {
                throw Exception(signUpResponse.message)
            }

            val userResponse = signUpResponse.data ?: throw Exception("Missing 'data' in response")
            val userDto = userResponse.user ?: throw Exception("Missing 'user' in response 'data'")

            val userDTOMapper = UserDTOMapper()
            userDTOMapper.fromUserDtoToUserDomain(userDto)
        }
    }
}