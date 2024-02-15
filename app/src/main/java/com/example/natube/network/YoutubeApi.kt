package com.example.natube.network



import com.example.natube.model.searchmodel.SearchModel
import com.example.natube.model.videomodel.VideoModel
import retrofit2.http.GET
import retrofit2.http.Query


interface YoutubeAPI {
    companion object{
        private const val API_MAX_RESULT = 16
        private const val API_REGION = "KR"
        private const val YOUTUBE_API_KEY = "AIzaSyAXm5vhewIEwB2gKJcGsQIeivHIp6KCF2k"
    }
    @GET("videos")
    suspend fun getTrendingVideos(
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = API_MAX_RESULT,
        @Query("regionCode") regionCode: String = API_REGION,
        @Query("key") apiKey: String = YOUTUBE_API_KEY,
        @Query("chart") chart : String = "mostPopular",
        @Query("videoCategoryId") videoCategoryId : String,
        @Query("pageToken") nextPageToken: String="",
    ) : VideoModel

    @GET("search")
    suspend fun getSearchingVideos(
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = API_MAX_RESULT*2,
        @Query("regionCode") regionCode: String = API_REGION,
        @Query("key") apiKey: String = YOUTUBE_API_KEY,
        @Query("q") query : String,
        @Query("pageToken") nextPageToken: String ="",
    ) : SearchModel
}