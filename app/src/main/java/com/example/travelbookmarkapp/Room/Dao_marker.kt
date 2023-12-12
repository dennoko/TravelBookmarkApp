package com.example.travelbookmarkapp.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao_marker {
    @Insert
    suspend fun insertPhoto(locateInfo: Entity_marker)

    @Update
    suspend fun updatePhoto(locateInfo: Entity_marker)

    @Query("SELECT * FROM Entity_marker")
    fun getAll(): Flow<List<Entity_marker>>

    // idを元に、対象のデータにuriを追加する
    @Query("UPDATE Entity_marker SET uri = :uri WHERE id = :id")
    suspend fun updateUri(id: Int, uri: String)

    @Query("SELECT * FROM Entity_marker WHERE id = :id")
    suspend fun getEntityById(id: Int): Entity_marker?


    // 緯度と経度を元に、Entity_markerのuriを取得する
    @Query("SELECT uri FROM Entity_marker WHERE latitude = :latitude AND longitude = :longitude LIMIT 1")
    suspend fun getUriByLatLng(latitude: Double, longitude: Double): String?

    // 緯度と経度を元に、Entity_markerのidを取得する
    @Query("SELECT id FROM Entity_marker WHERE latitude = :latitude AND longitude = :longitude LIMIT 1")
    suspend fun getIdByLatLng(latitude: Double, longitude: Double): Int?
}