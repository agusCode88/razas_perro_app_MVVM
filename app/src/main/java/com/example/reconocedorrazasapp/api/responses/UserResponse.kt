package com.example.reconocedorrazasapp.api.responses

import com.example.reconocedorrazasapp.api.dto.UserDTO
import com.squareup.moshi.Json

class UserResponse (
    @Json(name = "user")
    val user: UserDTO)