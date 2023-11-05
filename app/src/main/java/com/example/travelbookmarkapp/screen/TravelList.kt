package com.example.travelbookmarkapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TravelList() {
    // 旅行のデータを格納するリスト
    var travelList by remember { mutableStateOf(mutableListOf("title1", "title2"))}

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        LazyColumn(Modifier.clickable { /*完成した予定の画面に移動*/  }) {
            items(travelList) { title ->
                Text(text = title)
            }
        }

        Button(onClick = { /*新しく予定を作る画面に移動*/ }) {
            Text(text = "add new travel")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTravelList() {
    TravelList()
}