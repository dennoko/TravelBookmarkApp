package com.example.travelbookmarkapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.travelbookmarkapp.firebase_components.FirestoreViewModel
import kotlinx.coroutines.launch

@Composable
fun TravelList(navController: NavController) {
    val viewModel = remember { FirestoreViewModel() }

    // 旅行のデータを格納するリスト
    var travelList by remember { mutableStateOf(listOf<FirestoreViewModel.travelListData>()) }

    //非同期でFirestoreからTravelDataを取得
    LaunchedEffect(true) {
        viewModel.loadTravelDataFromFirestore()
        travelList = viewModel.travelList
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        if (travelList.isNotEmpty()) {
            LazyColumn{
                items(travelList) { travelData ->
                    Row (
                        modifier = Modifier.clickable{ /* ドキュメントID("年月日時分タイトル")を渡しつつ完成した旅行の予定の画面に移動 */ }
                    ) {
                        Text(text = travelData.title)
                        Text(text = "：")
                        Text(text = "${travelData.depYear}年${travelData.depMonth}月${travelData.depDay}日")
                    }
                }
            }
        }
        else {
            Text(text = "旅行の予定はありません")
        }

        //InputScheduleに移動
        Button(onClick = { navController.navigate("inputschedule") }) {
            Text(text = "add new travel")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTravelList() {
    TravelList(navController = rememberNavController())
}