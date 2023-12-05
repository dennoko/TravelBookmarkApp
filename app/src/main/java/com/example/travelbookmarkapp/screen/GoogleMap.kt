package com.example.travelbookmarkapp.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DirectionMap(name: List<String>, modifier: Modifier = Modifier) {
    val singapore = LatLng(35.6809591, 139.7673068)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    val polylinePoints = name
        .flatMap { PolyUtil.decode(it) }


    //ResultScreen("${polylinePoints}", Modifier)

    DisposableEffect(name) {
        onDispose {
            // クリーンアップ
        }
    }
    GoogleMap(
        modifier = Modifier.size(100.dp),
        cameraPositionState = cameraPositionState
    ){
        Marker(
            state = MarkerState(position = singapore),
            title = "Singapore",
            snippet = "Marker in Singapore"
        )
        Polyline(
            points = polylinePoints,
            color = Color.Blue,
            zIndex = 1f
        )
    }
}