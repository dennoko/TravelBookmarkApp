package com.example.travelbookmarkapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.travelbookmarkapp.firebase_components.DeleteTravelDataViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun ScheduleDetail(navController: NavController) {
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val arguments = navBackStackEntry?.arguments

    val viewModel = remember { DeleteTravelDataViewModel() }

    // TravelListから渡されたドキュメントIDを取得
    val title = arguments?.getString("title").toString()
    val departure = arguments?.getString("departure").toString()
    val depYear = arguments?.getString("depYear").toString()
    val depMonth = arguments?.getString("depMonth").toString()
    val depDay = arguments?.getString("depDay").toString()
    val depHour = arguments?.getString("depHour").toString()
    val depMinute = arguments?.getString("depMinute").toString()
    val destination = arguments?.getString("destination").toString()
    val desYear = arguments?.getString("desYear").toString()
    val desMonth = arguments?.getString("desMonth").toString()
    val desDay = arguments?.getString("desDay").toString()
    val desHour = arguments?.getString("desHour").toString()
    val desMinute = arguments?.getString("desMinute").toString()
    val todoList = arguments?.getString("todoList").toString().split(", ")
    val documentID = arguments?.getString("documentID").toString()

    Column {
        // 詳細情報を表示
        Text(text = "タイトル：$title")
        Text(text = "出発地：$departure")
        Text(text = "出発時間：${depYear}年${depMonth}月${depDay}日${depHour}時${depMinute}分")
        Text(text = "目的地：$destination")
        Text(text = "到着時間：${desYear}年${desMonth}月${desDay}日${desHour}時${desMinute}分")
        Text(text = "TODOリスト")
        todoList.forEach {
            Text(text = it)
        }

        Button(onClick = { navController.navigate("II_GoogleMap") }) {
            Text(text = "地図を表示")
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "戻る")
            }

            // 旅行データを削除
            Button(onClick = {
                scope.launch {
                    viewModel.deleteTravelDataFromFirestore(documentID)
                    navController.popBackStack()
                }
            }) {
                Text(text = "削除")
            }

            Button(onClick = {
                navController.navigate("editschedule/${title}/${departure}/${depYear}/${depMonth}/${depDay}/${depHour}/${depMinute}/${destination}/${desYear}/${desMonth}/${desDay}/${desHour}/${desMinute}/${todoList.joinToString { it }}/${documentID}")
            }) {
                Text(text = "編集")
            }
        }
    }
}