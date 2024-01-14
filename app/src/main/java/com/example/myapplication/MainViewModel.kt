import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.CountryRepository
import com.example.myapplication.repository.model.Country
import com.example.myapplication.repository.model.CountryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val countryRepository = CountryRepository()
    val mutableCountriesData = MutableLiveData<List<CountryResponse>>()
    val immutableCountiesData: LiveData<List<CountryResponse>> = mutableCountriesData

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = countryRepository.getCountryResponse()
                Log.d("MainViewModel", "request: ${request.raw()}")

                if(request.isSuccessful){
                    request.message()
                    val countries = request.body()
                    Log.d("MainViewModel", "Request body: $countries")
                    mutableCountriesData.postValue(countries)
                }


            } catch (e: Exception) {
                Log.e("MainViewModel", "Operacja nie powiodla sie ${e}", e)
            }
        }
    }
}

