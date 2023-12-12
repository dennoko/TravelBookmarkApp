package com.example.travelbookmarkapp.network

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import retrofit2.http.GET
import androidx.lifecycle.viewModelScope
import com.example.travelbookmarkapp.model.GooglePlacesInfo
import kotlinx.coroutines.launch
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.Query

//https://maps.googleapis.com/maps/api/directions/json?origin=東京駅&destination=スカイツリー&key=AIzaSyD-pe2PbCI5MK_tAuRmxs-_z09d8njZRMk
private const val BASE_URL =
    "https://maps.googleapis.com/maps/api/directions/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json {
        ignoreUnknownKeys = true
    }.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface MarsApiService {

    val origin: String
    val destination: String

    @GET("json")
    suspend fun getPhotos(
        @Query("origin") origin: String = this.origin,
        @Query("destination") destination: String = this.destination,
        @Query("key") apiKey: String = "AIzaSyD-pe2PbCI5MK_tAuRmxs-_z09d8njZRMk"
    ): GooglePlacesInfo
}


object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}

sealed interface MarsUiState {
    data class Success(val photos: GooglePlacesInfo) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel : ViewModel() {
    var origin:String = ""
    var destination:String = ""


    var marsUiState: List<String> by mutableStateOf(emptyList())
        private set

    init {
        Log.d("aa", "ss")
        viewModelScope.launch {
            try {
                while(origin == "") {}
                getMarsPhotos(origin, destination)
            } catch (e: Exception) {
                Log.e("MarsViewModel", "Error: ${e.message}", e)
            }
            Log.d("error1", "${origin}")
        }
    }


    private suspend fun getMarsPhotos(origin: String, destination: String) {

            // API を呼び出し、ルートデータを取得
            val listResult =
                MarsApi.retrofitService.getPhotos(origin = origin, destination = destination).routes
            Log.d("error567", "${listResult}")
            // 取得したデータを加工してUI状態に設定
            val pointsList: List<String> =
                listResult.flatMap { it.overview_polyline.points.split(",") }
            Log.d("error44", "ee")
            marsUiState = pointsList

    }
}