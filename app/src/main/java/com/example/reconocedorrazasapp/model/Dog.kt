package com.example.reconocedorrazasapp.model

import com.squareup.moshi.Json

data class Dog(
    val id: Long,
    val index: Int,
    val name: String,
    val type:String,
    val heightFemale: Double,
    val heightMale: Double,
    val imageUrl: String,
    val lifeExpectancy: String,
    val temperament: String,
    val weightFemale: String,
    val weightMale: String
) {}