package com.example.travelbookmarkapp.Room

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "Entity_marker")
data class Entity_marker (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val latitude: Double?,
    val longitude: Double?,
    val uri: String?
)