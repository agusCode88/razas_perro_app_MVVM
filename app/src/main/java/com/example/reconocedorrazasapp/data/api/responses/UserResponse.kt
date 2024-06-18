package com.example.reconocedorrazasapp.data.api.responses

import com.example.reconocedorrazasapp.data.api.dto.UserDTO
import com.squareup.moshi.Json

class UserResponse (
    @Json(name = "user")
    val user: UserDTO
)