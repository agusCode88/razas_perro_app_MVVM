package com.example.reconocedorrazasapp.api

import com.example.reconocedorrazasapp.api.responses.DogListApiResponse
import com.example.reconocedorrazasapp.model.Dog
import com.example.reconocedorrazasapp.utils.BASE_URL
import com.example.reconocedorrazasapp.utils.GET_DOGS
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()



private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface ApiService{
    @GET(GET_DOGS)
    suspend fun fetchAllDogs(): DogListApiResponse
}

object DogsApi{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}