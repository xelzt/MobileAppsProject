package com.example.myapplication.repository

import com.example.myapplication.repository.model.CountryResponse
import retrofit2.Response

class CountryRepository {

    suspend fun getCountryResponse(): Response<List<CountryResponse>> =
        CountryService.countryService.getCountryResponse()

}