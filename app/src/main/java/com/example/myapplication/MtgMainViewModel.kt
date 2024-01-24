import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.MTGApiTest.model.Card
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MtgMainViewModel : ViewModel() {

    private val mtgReposity = MTGRepository()

    private val mutableMtgCardsData = MutableLiveData<List<Card>>()
    val immutableMtgCardsData: LiveData<List<Card>> = mutableMtgCardsData

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = mtgReposity.getMTGResponse()
                Log.d("com.example.myapplication.MainViewModel", "request: ${request.raw()}")

                if(request.isSuccessful){
                    request.message()
                    val countries = request.body()?.cards
                    Log.d("com.example.myapplication.MainViewModel", "Request body: $countries")
                    mutableMtgCardsData.postValue(countries)
                }

            } catch (e: Exception) {
                Log.e("com.example.myapplication.MainViewModel", "Operacja nie powiodla sie", e)
            }
        }
    }
}