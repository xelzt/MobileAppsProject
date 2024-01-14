package com.example.myapplication.MTGApiTest

import com.example.myapplication.MTGApiTest.model.MTGResponse
import com.example.myapplication.repository.CountryService
import com.example.myapplication.repository.model.CountryResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MTGService {

    @GET("cards")
    suspend fun getMTGResponse(): Response<MTGResponse>

    companion object {
        private const val MTG_URL = "https://api.magicthegathering.io/v1/"

        private val logger = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private val okHttp = OkHttpClient.Builder().apply {
            this.addInterceptor(logger)
        }.build()

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(MTG_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val mtgService: MTGService by lazy {
            retrofit.create(MTGService::class.java)
        }
    }

}