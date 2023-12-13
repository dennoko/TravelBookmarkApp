package com.example.travelbookmarkapp.directionAPI

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbookmarkapp.model.Routes
import kotlinx.coroutines.launch

class DirectionVM: ViewModel() {
    private val repository = DirectionRepository()

    var routePointsList: List<String> by mutableStateOf(emptyList())
        private set

    var polyline: List<Routes>? = null

    fun getDirection(origin: String, destination: String) {
        viewModelScope.launch {
            try {
                Log.d("methodTest", "repository.getDirection")
                polyline = repository.getDirection(origin, destination).routes
            }catch (e: Exception) {
                Log.d("methodTest", "error: $e")
            }

            val pointsList: List<String> = polyline!!.flatMap {
                it.overview_polyline.points.split(",")
            }

            routePointsList = pointsList
        }
    }
}