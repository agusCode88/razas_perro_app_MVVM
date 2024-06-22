package com.example.reconocedorrazasapp.data.api.connection

import com.example.reconocedorrazasapp.data.api.dto.AddDogToUserDTO
import com.example.reconocedorrazasapp.data.api.dto.LoginDTO
import com.example.reconocedorrazasapp.data.api.dto.SignUpDTO
import com.example.reconocedorrazasapp.data.api.responses.AuthApiResponse
import com.example.reconocedorrazasapp.data.api.responses.DefaultResponse
import com.example.reconocedorrazasapp.data.api.responses.DogListApiResponse
import com.example.reconocedorrazasapp.utils.ADD_DOG
import com.example.reconocedorrazasapp.utils.BASE_URL
import com.example.reconocedorrazasapp.utils.GET_DOGS
import com.example.reconocedorrazasapp.utils.GET_USER_DOGS_URL
import com.example.reconocedorrazasapp.utils.LOGIN_URL
import com.example.reconocedorrazasapp.utils.SIGN_UP_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
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

    @Headers("${ ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY } : true")
    @POST(ADD_DOG)
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse

    @Headers("${ ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY } : true")
    @GET(GET_USER_DOGS_URL)
    suspend fun getUserDogs(): DogListApiResponse


}

object DogsApi{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}