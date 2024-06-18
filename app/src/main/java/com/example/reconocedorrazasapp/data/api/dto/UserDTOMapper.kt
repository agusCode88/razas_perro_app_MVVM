package com.example.reconocedorrazasapp.data.api.dto

import com.example.reconocedorrazasapp.domain.model.Dog
import com.example.reconocedorrazasapp.domain.model.User

class UserDTOMapper {
     fun fromUserDtoToUserDomain(userDTO: UserDTO): User {
        return User(
            userDTO.id,
            userDTO.email,
            userDTO.authenticationToken
        )
    }
}