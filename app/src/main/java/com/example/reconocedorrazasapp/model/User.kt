package com.example.reconocedorrazasapp.model

import com.squareup.moshi.Json

class User(
    val id: Long,
    val email: String,
    val authenticationToken: String
)