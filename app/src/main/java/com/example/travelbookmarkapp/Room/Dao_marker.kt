package com.example.travelbookmarkapp.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao_marker {
    @Insert
    suspend fun insertPhoto(locateInfo: Entity_marker)

    @Query("SELECT * FROM Entity_marker")
    fun getAll(): Flow<List<Entity_marker>>

}