package com.example.travelbookmarkapp.screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.travelbookmarkapp.Room.Database_marker
import com.example.travelbookmarkapp.Room.Entity_marker
import com.example.travelbookmarkapp.ui_components.ImagesWindow
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

@Composable
fun GoogleMap_r_refactoring(db: Database_marker) {
    // Google Map 用の変数
    val tokyo = LatLng(35.681167, 139.767052)
    // マップの初期位置を設定
    val cameraPositionState = rememberCameraPositionState() {
        position = CameraPosition.fromLatLngZoom(tokyo, 10f)
    }
    // マップをタップした時の位置情報を取得
    val coroutineScope = rememberCoroutineScope()
    fun onMapClick(latLng: LatLng) {
        // データベースに位置情報を保存
        // 非同期処理の開始
        coroutineScope.launch {
            // 非同期処理のスレッドをUIスレッドからIOスレッドに変更
            withContext(Dispatchers.IO) {
                // データベースに位置情報を保存
                db.addPhoto(latLng.latitude, latLng.longitude, null)
            }
        }
    }


    // データベースから保存済みの位置情報を取得
    // データのリスト
    var markerList by remember {
        mutableStateOf(emptyList<Entity_marker>())
    }
    // 非同期でデータベースからデータを取得
    LaunchedEffect(Unit) {
        Log.d("methodTest", "start LaunchedEffect")
        // スレッドをメインスレッドからIOスレッドに変更
        withContext(Dispatchers.IO) {
            // データベースからデータを取得
            db.daoMarker().getAll().collect() {
                Log.d("methodTest", "start collect")
                // データをリストに保存
                markerList = it
                Log.d("methodTest", "End collect") // データの取得確認用
                //Log.d("methodTest", "photoList = $photoList") // データの取得確認用
            }
        }
    }


    // 画像の表示関係
    // ImagesWindow の表示、非表示の管理
    var isImagesWindowVisible by remember { mutableStateOf(false) }
    // ImagesWindow に渡すmarkerの情報
    var markerInfo: Entity_marker? by remember { mutableStateOf(null) }


//    // 画像の追加を行う関数
//    // 保存するURIを格納する変数
//    var saveUri: Uri? by remember { mutableStateOf(null) }
//    // 画像の追加を行う関数
//    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { photoUri: Uri? ->
//        if (photoUri != null) {
//            saveUri = photoUri
//            Log.d("methodTest", "saveUri: $saveUri")
//        }
//    }
//
//    fun addPhoto(latLng: LatLng) {
//        getContent.launch("image/*")

    // 画像の追加を行う関数
    // 保存するURIを格納する変数
    var saveUriList: List<@JvmSuppressWildcards Uri>? by remember { mutableStateOf(null) }
    //
    val pickMediaRequest = PickVisualMediaRequest()
    // 画像の追加を行う関数
    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { photoUri: List<@JvmSuppressWildcards Uri> ->
        if (photoUri != null) {
            saveUriList = photoUri
            Log.d("saveUri", "saveUri: $saveUriList")
        }
    }

    fun addPhoto(latLng: LatLng) {
        getContent.launch(pickMediaRequest)

        // 非同期でデータベースに画像のURIを保存
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                // 緯度と経度からエンティティのidを取得
                val id = db.daoMarker().getIdByLatLng(latLng.latitude, latLng.longitude)

                // saveUriがnullでなくなるまで待機
                Log.d("methodTest", "waiting")
                while(saveUriList == null ) {
                    delay(100)
                }
                Log.d("methodTest", "end waiting")

                // エンティティを取得
                val entity = db.daoMarker().getEntityById(id!!)
                // 保存するURIのString
                var saveUriStr: String? = null
                // エンティティのuriに画像のURIを追加
                if(entity != null) {
                    var addUriStr: String = ""

                    //saveUriListの中身を1つのUriにする
                    saveUriList?.let {
                        saveUriList!!.forEach { uri ->
                            addUriStr += " " + uri
                        }
                    }

                    saveUriStr = entity.uri + addUriStr
                }

                // idを元に、対象のデータにuriを追加する
                if(id != null && saveUriStr != null) {
                    Log.d("methodTest", "save uri add Database: $saveUriList")
                    db.daoMarker().updateUri(id, saveUriStr!!)
                }

                saveUriList = null
            }
        }
    }


    // UI
    Box {
        // Google Map
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { clickPosition ->
                Log.d("methodTest", "Start onMapClick")
                onMapClick(clickPosition) // データベースに位置情報を保存
                Log.d("methodTest", "End onMapClick. Saved $clickPosition")
            },
        ) {
            // マーカーの情報を取得
            markerList.forEachIndexed {index , markerPosition ->
                // マーカーの位置情報
                val markerLatLng = LatLng(markerPosition.latitude!!, markerPosition.longitude!!)

                // マーカーを表示
                Marker(
                    state = MarkerState(position = markerLatLng),
                    title = "思い出の地$index",
                    snippet = "長押しで写真を追加",
                    onInfoWindowClick = {
                        Log.d("methodTest", "Start onInfoWindowClick")
                        // ImagesWindow に渡すmarkerの情報を更新
                        markerInfo = markerPosition
                        isImagesWindowVisible = !isImagesWindowVisible
                        Log.d("methodTest", "markerInfo = $markerInfo")
                    },
                    onInfoWindowLongClick = {
                        isImagesWindowVisible = false
                        Log.d("methodTest", "Start onInfoWindowLongClick")
                        addPhoto(markerLatLng)
                        Log.d("methodTest", "End onInfoWindowLongClick")
                    }
                )
            }
        }

        // ImagesWindow
        if (isImagesWindowVisible) {
            Log.d("methodTest", "Start ImagesWindow. markerInfo = $markerInfo")
            ImagesWindow(markerInfo!!, db)
        }
    }
}