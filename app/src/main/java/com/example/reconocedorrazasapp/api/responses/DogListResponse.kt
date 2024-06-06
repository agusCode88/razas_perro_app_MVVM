package com.example.reconocedorrazasapp.api.responses

import com.example.reconocedorrazasapp.model.Dog

data class DogListResponse(val dogs: MutableList<Dog>)