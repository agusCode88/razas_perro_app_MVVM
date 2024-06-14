package com.example.reconocedorrazasapp.api.dto

import com.example.reconocedorrazasapp.model.Dog
import com.example.reconocedorrazasapp.model.User

class UserDTOMapper {
     fun fromUserDtoToUserDomain(userDTO: UserDTO): User {
        return User(
            userDTO.id,
            userDTO.email,
            userDTO.authenticationToken
        )
    }
}