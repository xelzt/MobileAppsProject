package com.example.myapplication.repository

import com.example.myapplication.repository.model.Country
import com.example.myapplication.repository.model.CountryResponse
import retrofit2.Response
import retrofit2.http.Path

class CountryRepository {

    suspend fun getCountryResponse(): Response<List<CountryResponse>> =
        CountryService.countryService.getCountryResponse()

    suspend fun getCountryDetailsResponse(name: String): Response<List<Country>> =
        CountryService.countryService.getCountryDetailsResponse(name)

}