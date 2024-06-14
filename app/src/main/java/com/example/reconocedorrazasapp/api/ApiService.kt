package com.example.reconocedorrazasapp.api

import com.example.reconocedorrazasapp.api.dto.LoginDTO
import com.example.reconocedorrazasapp.api.dto.SignUpDTO
import com.example.reconocedorrazasapp.api.responses.AuthApiResponse
import com.example.reconocedorrazasapp.api.responses.DogListApiResponse
import com.example.reconocedorrazasapp.utils.BASE_URL
import com.example.reconocedorrazasapp.utils.GET_DOGS
import com.example.reconocedorrazasapp.utils.LOGIN_URL
import com.example.reconocedorrazasapp.utils.SIGN_UP_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


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

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDto: SignUpDTO): AuthApiResponse

    @POST(LOGIN_URL)
    suspend fun login(@Body loginDto: LoginDTO): AuthApiResponse


}

object DogsApi{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}