package com.example.travelbookmarkapp.directionAPI

import android.util.Log
import com.example.travelbookmarkapp.model.GooglePlacesInfo
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

// インターフェースでエンドポイントを定義
interface DirectionService {
    val origin: String
    val destination: String

    @GET("json")
    suspend fun getDirections(
        @Query("origin") origin: String = this.origin,
        @Query("destination") destination: String = this.destination,
        @Query("key") apiKey: String = "AIzaSyD-pe2PbCI5MK_tAuRmxs-_z09d8njZRMk"
    ): GooglePlacesInfo
}

// Retrofit のインスタンスの作成
object RetrofitInstance {
    private const val BASE_URL = "https://maps.googleapis.com/maps/api/directions/"

    private val retrofit: Retrofit by lazy {
        Log.d("methodTest", "Retrofit.Builder")
        Retrofit.Builder()
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
    }

    val directionService: DirectionService by lazy {
        retrofit.create(DirectionService::class.java)
    }
}

// Repository class
class DirectionRepository {
    private val directionService = RetrofitInstance.directionService

    suspend fun getDirection(origin: String, destination: String): GooglePlacesInfo {
        return directionService.getDirections(origin = origin, destination = destination)
    }
}