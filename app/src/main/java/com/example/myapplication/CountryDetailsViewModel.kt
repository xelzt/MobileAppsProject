package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.CountryRepository
import com.example.myapplication.repository.UiState
import com.example.myapplication.repository.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryDetailsViewModel : ViewModel() {

    private val countryRepository = CountryRepository()
    private val mutableCountryDetailsData = MutableLiveData<UiState<Country>>()
    val immutableCountryDetailsData: LiveData<UiState<Country>> = mutableCountryDetailsData

    fun getData(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = countryRepository.getCountryDetailsResponse(name)

                if(request.isSuccessful){
                    request.message()
                    val countryDetails = request.body()
                    mutableCountryDetailsData.postValue(UiState(countryDetails?.get(0)))
                }


            } catch (e: Exception) {
                Log.e("CountryDetailsViewModel", "Operacja nie powiodla sie $e", e)
            }
        }
    }

}