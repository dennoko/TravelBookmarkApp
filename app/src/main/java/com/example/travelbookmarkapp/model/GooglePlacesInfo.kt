package com.example.travelbookmarkapp.model

import kotlinx.serialization.Serializable

@Serializable
data class GooglePlacesInfo(
    val geocoded_waypoints: List<GeocodedWaypoints>,
    val routes: List<Routes>,
    val status: String
)
