package com.example.reconocedorrazasapp.data.api.responses

import com.squareup.moshi.Json

data class DogListApiResponse(
    val message: String,
    @Json(name = "is_success")
    val isSuccess: Boolean,
    val data: DogListResponse
)