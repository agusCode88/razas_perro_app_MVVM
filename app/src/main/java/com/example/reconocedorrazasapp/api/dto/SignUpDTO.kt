package com.example.reconocedorrazasapp.api.dto

import com.squareup.moshi.Json

data class SignUpDTO(
    val email: String,
    val password: String,
    @Json(name = "password_confirmation")
    val passwordConfrimation: String
)