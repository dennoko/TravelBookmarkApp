package com.example.travelbookmarkapp.Room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Entity_marker::class], version = 1, exportSchema = false)
abstract class Database_marker: RoomDatabase() {
    abstract fun daoMarker(): Dao_marker

    companion object {
        private var photoDB: Database_marker? = null

        fun getDB(context: Context): Database_marker {
            if (photoDB == null) {
                photoDB = Room.databaseBuilder(
                    context.applicationContext,
                    Database_marker::class.java,
                    "photo_database"
                ).build()
            }
            return photoDB!!
        }
    }

    suspend fun addPhoto(Lat: Double, Lng: Double, uri: String?) {
        Log.d("tag", "動作")
        val newPhoto = Entity_marker(latitude = Lat, longitude = Lng, uri = uri)
        daoMarker().insertPhoto(newPhoto)
        Log.d("tag", "$newPhoto")
    }
}