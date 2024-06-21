package com.example.reconocedorrazasapp.data.api.responses

import com.squareup.moshi.Json

data class DefaultResponse(
    val message: String,
    @Json(name = "is_success")
    val isSuccess: Boolean,
)