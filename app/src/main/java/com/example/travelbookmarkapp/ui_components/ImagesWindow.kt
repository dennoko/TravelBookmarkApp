package com.example.travelbookmarkapp.ui_components

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.travelbookmarkapp.Room.Database_marker
import com.example.travelbookmarkapp.Room.Entity_marker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ImagesWindow(marker: Entity_marker, db: Database_marker) {
    Log.d("methodTest", "start ImagesWindow")
    // データベースから画像のURIの文字列を取得する
    // 画像のURIの文字列を格納する変数
    var uriStr: String?
    // uriのリスト
    var uriList = remember{ mutableStateOf(emptyList<String>())}


    // 非同期でデータを取得
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            // データベースから画像のURIの文字列を取得する
            Log.d("methodTest", "start LaunchedEffect")
            uriStr = db.daoMarker().getUriByLatLng(marker.latitude!!, marker.longitude!!)
            Log.d("methodTest", "uriStr: $uriStr")

            if(uriStr != null) {
                // 文字列をsplitで分割してリストに格納する
                Log.d("methodTest", "initial uriList: $uriList , uriStr: $uriStr")
                uriList.value = uriStr!!.split(" ")
                Log.d("methodTest", "uriList: $uriList")
            }
        }
    }
    
    Card(modifier = Modifier.size(200.dp)) {
        // 画像がある場合は画像を表示する
        if(uriList.value.isNotEmpty()) {
            LazyColumn {
                items(uriList.value) { uri ->
                    Log.d("methodTest", "uri: $uri")
                    val uri = uri.toUri()

                    // Coil で画像を表示する
                    AsyncImage(uri, contentDescription = null)
                }
            }
        }else{
            // 画像がない場合はテキストを表示する
            Text(text = "画像はありません")
        }
    }
}