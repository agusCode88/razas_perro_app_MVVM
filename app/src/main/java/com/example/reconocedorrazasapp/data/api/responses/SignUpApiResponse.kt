package com.example.reconocedorrazasapp.data.api.responses

import com.squareup.moshi.Json

class AuthApiResponse(
    val message: String,
    @Json(name = "is_success")
    val isSuccess: Boolean,
    val data: UserResponse?
)