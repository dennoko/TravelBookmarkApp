package com.example.travelbookmarkapp.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.travelbookmarkapp.Room.Database_marker
import com.example.travelbookmarkapp.Room.Entity_marker
import com.example.travelbookmarkapp.ui.theme.TravelBookmarkAppTheme
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun photoOnMap(context: Context, database: Database_marker) {
    val singapore = LatLng(1.35, 103.87)

    var photoList by remember {
        mutableStateOf(emptyList<Entity_marker>())
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        Log.d("tag", "start")
        withContext(Dispatchers.IO) {
            database.daoMarker().getAll().collect() {
                Log.d("tag", "開始")
                photoList = it
                Log.d("tag", "$it")
            }
        }
        Log.d("tag", "finish")
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    // 保存用のuri
    var saveUri: Uri? by remember { mutableStateOf(null) }

    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            imageUris = imageUris + uri

            saveUri = uri
        }
    }

    var zoom by remember { mutableStateOf(cameraPositionState.position.zoom.toInt()) }
    zoom = cameraPositionState.position.zoom.toInt()
    val newWidth = 10 * zoom
    val newHeight = 10 * zoom

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = { clickPosition ->
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    database.addPhoto(Lat = clickPosition.latitude, Lng = clickPosition.longitude, uri = null)
                }
                Log.d("tag","$clickPosition")
            }
        }
    ) {
        // 既存のマーカーを表示
        photoList.forEachIndexed { index, markerPosition ->
            // uriをroomから取得
            var imageUri: Uri? = null
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    // 緯度と経度を元に、Entity_markerのuriを取得する
                    val uri = database.daoMarker().getUriByLatLng(markerPosition.latitude!!, markerPosition.longitude!!)
                    if (uri != null) {
                        Log.d("hoge", "uri$index: $uri")
                        imageUri = Uri.parse(uri)
                    }
                }
            }
            val resizedBitmap = imageUri?.let { uri ->
                getBitmapFromUri(uri, context)?.let { Bitmap.createScaledBitmap(it, newWidth, newHeight, false) }
            }
            val resizedIcon = resizedBitmap?.let { BitmapDescriptorFactory.fromBitmap(it) }

            val markerPosition = LatLng(markerPosition.latitude!!, markerPosition.longitude!!)

            var bottomBar by remember { mutableStateOf(false) }
            if(bottomBar){
                launchBottomBar()
            }

            Marker(
                state = MarkerState(position = markerPosition),
                title = "シンガポール",
                snippet = "タップして写真を追加",
                icon = resizedIcon,
                onInfoWindowClick = {
                                    bottomBar = !bottomBar
                },
                onInfoWindowLongClick = {
                    getContent.launch("image/*")

                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            val id = database.daoMarker().getIdByLatLng(markerPosition.latitude, markerPosition.longitude)

                            // saveUri がnullでなくなるまで待つ
                            while (saveUri == null) {
                                delay(100)
                            }

                            val entity = database.daoMarker().getEntityById(id!!)
                            if (entity != null) {
                                entity.uri = entity.uri + " " + saveUri.toString()
                            }

                            // idを元に、対象のデータにuriを追加する
                            if (id != null) {
                                Log.d("hoge", "save uri: $saveUri")
                                database.daoMarker().updateUri(id, saveUri.toString())
                            }
                        }
                    }
                }
            )
        }
    }
}


// URIからビットマップを取得するメソッド
@Composable
fun getBitmapFromUri(uri: Uri, context: Context): Bitmap? {
    val inputStream: InputStream? =context.contentResolver.openInputStream(uri)
    return BitmapFactory.decodeStream(inputStream)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun launchBottomBar(){
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        }
    ) {
            innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text =
                """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action button  times.
                """.trimIndent(),
            )
        }
    }
}