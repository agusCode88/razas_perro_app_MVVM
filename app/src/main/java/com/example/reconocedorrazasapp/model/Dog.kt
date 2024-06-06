package com.example.reconocedorrazasapp.model

import com.squareup.moshi.Json

data class Dog(
    val id: Long,
    val index: Int,
    @Json(name = "name_es")
    val name: String,
    @Json(name = "dog_type")
    val type:String,
    @Json(name = "height_female")
    val heightFemale: Double,
    @Json(name = "height_male")
    val heightMale: Double,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "life_expectancy")
    val lifeExpectancy: String,
    val temperament: String,
    @Json(name = "weight_female")
    val weightFemale: String,
    @Json(name = "weight_male")
    val weightMale: String
) {}