package com.example.travelbookmarkapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.travelbookmarkapp.firebase_components.saveTravelDataViewModel
import kotlinx.coroutines.launch

@Composable
fun ConfirmSchedule(navController: NavController) {
    val scope = rememberCoroutineScope()
    val viewModel = remember { saveTravelDataViewModel() }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val arguments = navBackStackEntry?.arguments

    // InputScheduleで入力したデータを取得
    val title = arguments?.getString("title") ?: ""
    val departure = arguments?.getString("departure") ?: ""
    val depYear = arguments?.getString("depYear") ?: ""
    val depMonth = arguments?.getString("depMonth") ?: ""
    val depDay = arguments?.getString("depDay") ?: ""
    val depHour = arguments?.getString("depHour") ?: ""
    val depMinute = arguments?.getString("depMinute") ?: ""
    val destination = arguments?.getString("destination") ?: ""
    val desYear = arguments?.getString("desYear") ?: ""
    val desMonth = arguments?.getString("desMonth") ?: ""
    val desDay = arguments?.getString("desDay") ?: ""
    val desHour = arguments?.getString("desHour") ?: ""
    val desMinute = arguments?.getString("desMinute") ?: ""
    val todoList = arguments?.getString("todoList")?.split(", ") ?: listOf("")

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "タイトル：$title")
        Text(text = "出発地：$departure")
        Text(text = "出発時間：${depYear}年${depMonth}月${depDay}日${depHour}時${depMinute}分")
        Text(text = "目的地：$destination")
        Text(text = "到着時間：${desYear}年${desMonth}月${desDay}日${desHour}時${desMinute}分")
        Text(text = "TODOリスト")
        LazyColumn() {
            items(todoList) { todo ->
                Text(text = todo)
            }
        }

        Text(text = "この内容で登録しますか？")

        Row {
            //InputScheduleに移動。データ渡しは未実装
            Button(onClick = { navController.navigate("inputschedule") }) {
                Text(text = "やめる")
            }

            //TravelListに移動。データ保存は未実装
            Button(onClick = {
                scope.launch {
                    viewModel.saveTravelDataToFirestore(
                        title = title,
                        departure = departure,
                        depYear = depYear,
                        depMonth = depMonth,
                        depDay = depDay,
                        depHour = depHour,
                        depMinute = depMinute,
                        destination = destination,
                        desYear = desYear,
                        desMonth = desMonth,
                        desDay = desDay,
                        desHour = desHour,
                        desMinute = desMinute,
                        todoList = todoList
                    )
                }
                navController.navigate("travellist")
            }) {
                Text(text = "登録")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewConfirmSchedule() {
    ConfirmSchedule(navController = rememberNavController())
}