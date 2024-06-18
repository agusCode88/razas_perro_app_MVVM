package com.example.reconocedorrazasapp.repository

import com.example.reconocedorrazasapp.data.api.connection.ApiResponseStatus
import com.example.reconocedorrazasapp.data.api.connection.DogsApi
import com.example.reconocedorrazasapp.data.api.dto.LoginDTO
import com.example.reconocedorrazasapp.data.api.dto.SignUpDTO
import com.example.reconocedorrazasapp.data.api.dto.UserDTOMapper
import com.example.reconocedorrazasapp.data.api.connection.makeNetWorkCall
import com.example.reconocedorrazasapp.domain.model.User

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


    suspend fun login(email: String, password: String): ApiResponseStatus<User> {
        return makeNetWorkCall {
            val loginDto = LoginDTO(email, password)
            val loginResponse = DogsApi.retrofitService.login(loginDto)

            if (!loginResponse.isSuccess ) {
                throw Exception(loginResponse.message)
            }

            val userResponse = loginResponse.data ?: throw Exception("Missing 'data' in response")
            val userDto = userResponse.user ?: throw Exception("Missing 'user' in response 'data'")

            val userDTOMapper = UserDTOMapper()
            userDTOMapper.fromUserDtoToUserDomain(userDto)
        }
    }


}