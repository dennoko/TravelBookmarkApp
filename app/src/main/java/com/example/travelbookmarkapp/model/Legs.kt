package com.example.travelbookmarkapp.model

import com.example.travelbookmarkapp.model.Distance
import com.example.travelbookmarkapp.model.Duration
import kotlinx.serialization.Serializable

@Serializable
data class Legs(
    val distance: Distance,
    val duration: Duration
)
