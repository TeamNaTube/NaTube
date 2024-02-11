import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.natube.model.videomodel.VideoModel
import com.example.natube.network.YoutubeAPI
import com.example.yourapp.models.VideoModel
import com.example.yourapp.network.YoutubeApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _searchResults = MutableLiveData<List<VideoModel>>()
    val searchResults: LiveData<List<VideoModel>> get() = _searchResults

    fun searchVideos(query: String) {

        // Retrofit을 사용하여 YouTube API로부터 동영상 검색
        YoutubeAPI.search(query).enqueue(object : Callback<List<VideoModel>> {
            override fun onResponse(call: Call<List<VideoModel>>, response: Response<List<VideoModel>>) {
                if (response.isSuccessful) {
                    _searchResults.value = response.body()
                } else {
                    // API 요청 실패 시 처리
                }
            }

            override fun onFailure(call: Call<List<VideoModel>>, t: Throwable) {
                // 네트워크 오류 또는 기타 오류 발생 시 처리
            }
        })
    }
}
