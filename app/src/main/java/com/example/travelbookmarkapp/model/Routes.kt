package com.example.travelbookmarkapp.model

import com.example.travelbookmarkapp.model.Legs
import com.example.travelbookmarkapp.model.OverviewPolyline
import kotlinx.serialization.Serializable

@Serializable
data class Routes(
    val summary: String,
    val overview_polyline: OverviewPolyline,
    val legs: List<Legs>
)
