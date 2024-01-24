package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.CountryRepository
import com.example.myapplication.repository.UiState
import com.example.myapplication.repository.model.CountryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val countryRepository = CountryRepository()
    private val mutableCountriesData = MutableLiveData<UiState<List<CountryResponse>>>()
    val immutableCountriesData: LiveData<UiState<List<CountryResponse>>> = mutableCountriesData

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = countryRepository.getCountryResponse()
                Log.d("com.example.myapplication.MainViewModel", "request: ${request.raw()}")

                if(request.isSuccessful){
                    request.message()
                    val countries = request.body()

                    Log.d("com.example.myapplication.MainViewModel", "Request body: $countries")
                    mutableCountriesData.postValue(UiState(countries))
                }


            } catch (e: Exception) {
                Log.e("com.example.myapplication.MainViewModel", "Operacja nie powiodla sie $e", e)
            }
        }
    }
}

