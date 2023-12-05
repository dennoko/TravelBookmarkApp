package com.example.travelbookmarkapp.model

import kotlinx.serialization.Serializable

@Serializable
data class GeocodedWaypoints(
    val geocoder_status: String,
    val place_id: String,
    val types: List<String>
)
