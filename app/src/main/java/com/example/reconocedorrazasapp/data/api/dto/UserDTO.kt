package com.example.reconocedorrazasapp.data.api.dto

import com.squareup.moshi.Json

class UserDTO(
    val id: Long,
    val email: String,
    @Json(name = "authentication_token")
    val authenticationToken: String
)