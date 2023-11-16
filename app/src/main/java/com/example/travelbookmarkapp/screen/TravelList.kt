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
import com.example.travelbookmarkapp.firebase_components.loadTravelListViewModel
import kotlinx.coroutines.launch

@Composable
fun TravelList(navController: NavController) {
    val viewModel = remember { loadTravelListViewModel() }

    // 旅行のデータを格納するリスト
    var travelList by remember { mutableStateOf(listOf<loadTravelListViewModel.TravelListData>()) }

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
                        modifier = Modifier.clickable{
                            // 選択した旅行のデータをScheduleDetailに渡しつつ画面遷移
                            navController.navigate(
                                "scheduledetail/${travelData.title}/${travelData.departure}/${travelData.depYear}/${travelData.depMonth}/${travelData.depDay}/${travelData.depHour}/${travelData.depMinute}/${travelData.destination}/${travelData.desYear}/${travelData.desMonth}/${travelData.desDay}/${travelData.desHour}/${travelData.desMinute}/${travelData.todoList.joinToString { it }}/${travelData.documentID}"
                            )
                        }
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