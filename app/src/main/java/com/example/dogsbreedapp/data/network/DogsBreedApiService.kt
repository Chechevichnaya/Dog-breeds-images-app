package com.example.dogsbreedapp.data.network

import com.example.dogsbreedapp.data.model.InfoAllBreedsFromApi
import com.example.dogsbreedapp.data.model.InfoImagesFromApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://dog.ceo"

val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

val converterFactory = Json.asConverterFactory("application/json".toMediaType())


private val retrofit = Retrofit.Builder()
    .addConverterFactory(converterFactory)
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface DogsBreedApiService {
    @GET("/api/breeds/list/all")
    suspend fun getAllBreeds(): InfoAllBreedsFromApi

    @GET("/api/breeds/image/random/10")
    suspend fun getRandomImages(): InfoImagesFromApi

    @GET("/api/breed/{breed}/images")
    suspend fun getCurrentBreedImages(@Path("breed") breedName: String): InfoImagesFromApi
}

object DogsBreedApi {
    val retrofitService: DogsBreedApiService by lazy {
        retrofit.create(DogsBreedApiService::class.java)
    }
}