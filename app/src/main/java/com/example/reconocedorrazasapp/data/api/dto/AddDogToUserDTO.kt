package com.example.reconocedorrazasapp.data.api.dto

import com.squareup.moshi.Json

data class AddDogToUserDTO(
    @Json(name = "dog_id")
    val dogId: String
)