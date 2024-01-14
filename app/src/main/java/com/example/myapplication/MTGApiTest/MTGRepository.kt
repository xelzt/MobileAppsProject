import com.example.myapplication.MTGApiTest.MTGService
import com.example.myapplication.MTGApiTest.model.MTGResponse
import retrofit2.Response

class MTGRepository {

    suspend fun getMTGResponse(): Response<MTGResponse> =
        MTGService.mtgService.getMTGResponse()

}