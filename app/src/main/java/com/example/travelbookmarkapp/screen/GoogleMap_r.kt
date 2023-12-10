package com.example.travelbookmarkapp.screen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import com.example.travelbookmarkapp.Room.Database_marker
import com.example.travelbookmarkapp.Room.Entity_marker
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@SuppressLint("SuspiciousIndentation")
@Composable
fun photoOnMap(context: Context, database: Database_marker) {

    var onClickPhotoList by remember { mutableStateOf(false) }
    var uri: String? = ""


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

    val getContent =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imageUris = imageUris + uri

                saveUri = uri
            }
        }

    var zoom by remember { mutableStateOf(cameraPositionState.position.zoom.toInt()) }
    zoom = cameraPositionState.position.zoom.toInt()
    val newWidth = 10 * zoom
    val newHeight = 10 * zoom

    Box {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { clickPosition ->
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        database.addPhoto(
                            Lat = clickPosition.latitude,
                            Lng = clickPosition.longitude,
                            uri = null
                        )
                    }
                    Log.d("tag", "$clickPosition")
                }
            }
        ) {
            //既存のマーカーを表示
            photoList.forEachIndexed { index, markerPosition ->
                // uriをroomから取得
//                var imageUri: Uri? = null
//                coroutineScope.launch {
////                    val getUri = async(Dispatchers.IO) {
//                    withContext(Dispatchers.IO) {
//                        // 緯度と経度を元に、Entity_markerのuriを取得する
//                        uri = database.daoMarker()
//                            .getUriByLatLng(markerPosition.latitude!!, markerPosition.longitude!!)
//                        Log.d("dbUri", "$uri")
//                    }
////                    uri = getUri.await()
//                }
                uri = executeAwait(markerPosition, database)
                if (uri != null) {
                    getPhotoList(uri = uri!!.toUri(), onClickPhotoList = onClickPhotoList)
                }
                Log.d("LatLngUri", "$uri")
//                if (uri != "") {
//                    while (true) {
//                        if (uri != null) {
//                            break
//                        }
//                    }
//                    imageUri = Uri.parse(uri)
//                    Log.d("imageUri", "$imageUri")
//                }


//                val resizedBitmap = imageUri?.let { uri ->
//                    getBitmapFromUri(uri, context)?.let { Bitmap.createScaledBitmap(it, newWidth, newHeight, false) }
//                }
//                val resizedIcon = resizedBitmap?.let { BitmapDescriptorFactory.fromBitmap(it) }

                val markerPosition = LatLng(markerPosition.latitude!!, markerPosition.longitude!!)




                Marker(
                    state = MarkerState(position = markerPosition),
                    title = "シンガポール",
                    snippet = "タップして写真を追加",
//                    icon = resizedIcon,
                    onInfoWindowClick = {
                        onClickPhotoList = !onClickPhotoList
                    },
                    onInfoWindowLongClick = {
                        getContent.launch("image/*")

                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                val id = database.daoMarker().getIdByLatLng(
                                    markerPosition.latitude,
                                    markerPosition.longitude
                                )

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
//        getPhotoList(uri = uri!!.toUri(), onClickPhotoList = onClickPhotoList)
    }
}

// 非同期内でセットした文字列を返す
fun executeAwait(markerPosition: Entity_marker, database: Database_marker): String? {
    var uri: String?
    runBlocking {
        withContext(Dispatchers.IO) {
            // 3秒スリープ（重たい処理）
            uri = database.daoMarker().getUriByLatLng(markerPosition.latitude!!, markerPosition.longitude!!)
        }
    }
    return uri
}


@Composable
fun getPhotoList (uri: Uri, onClickPhotoList: Boolean) {
    if (onClickPhotoList) {
        Log.d("uri!!", "$uri")
        //onClickPhotoList(uri!!.toString())

    }
}


//URIからビットマップを取得するメソッド
//@Composable
//fun getBitmapFromUri(uri: Uri, context: Context): Bitmap? {
//    val inputStream: InputStream? =context.contentResolver.openInputStream(uri)
//    return BitmapFactory.decodeStream(inputStream)
//}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun launchBottomBar(){
//    Scaffold(
//        bottomBar = {
//            BottomAppBar(
//                containerColor = MaterialTheme.colorScheme.primaryContainer,
//                contentColor = MaterialTheme.colorScheme.primary,
//            ) {
//                Text(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    textAlign = TextAlign.Center,
//                    text = "Bottom app bar",
//                )
//            }
//        }
//    ) {
//            innerPadding ->
//        Column(
//            modifier = Modifier
//                .padding(innerPadding),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//        ) {
//            Text(
//                modifier = Modifier.padding(8.dp),
//                text =
//                """
//                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.
//
//                    It also contains some basic inner content, such as this text.
//
//                    You have pressed the floating action button  times.
//                """.trimIndent(),
//            )
//        }
//    }
//}