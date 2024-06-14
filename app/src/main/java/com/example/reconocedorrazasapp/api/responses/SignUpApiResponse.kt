package com.example.reconocedorrazasapp.api.responses

import com.squareup.moshi.Json

class SignUpApiResponse(
    val message: String,
    @Json(name = "is_success")
    val isSuccess: Boolean,
    val data: UserResponse?
)