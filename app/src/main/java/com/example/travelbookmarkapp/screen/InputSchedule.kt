package com.example.travelbookmarkapp.screen

import android.view.textclassifier.TextClassificationSessionId
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.travelbookmarkapp.ui_components.DateTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputSchedule(navController: NavController) {

    var title by remember { mutableStateOf("") }
    var departure by remember { mutableStateOf("") }
    var depYear by remember { mutableStateOf("") }
    var depMonth by remember { mutableStateOf("") }
    var depDay by remember { mutableStateOf("") }
    var depHour by remember { mutableStateOf("") }
    var depMinute by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var desYear by remember { mutableStateOf("") }
    var desMonth by remember { mutableStateOf("") }
    var desDay by remember { mutableStateOf("") }
    var desHour by remember { mutableStateOf("") }
    var desMinute by remember { mutableStateOf("") }
    var todoTitle by remember { mutableStateOf("") }
    var todoList by remember { mutableStateOf(listOf("todo1", "todo2")) }

    val screenWidth = LocalConfiguration.current.screenWidthDp

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //旅行タイトル
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("タイトル") }
        )

        //出発地の入力欄
        OutlinedTextField(
            value = departure,
            onValueChange = { departure = it },
            label = { Text("出発地") }
        )

        //出発日時の入力欄
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){

            DateTextField(value = depYear, onValueChange = {depYear = it}, label = "年", screenWidth = screenWidth)
            DateTextField(value = depMonth, onValueChange = {depMonth = it}, label = "月", screenWidth = screenWidth)
            DateTextField(value = depDay, onValueChange = {depDay = it}, label = "日", screenWidth = screenWidth)
            DateTextField(value = depHour, onValueChange = {depHour = it}, label = "時", screenWidth = screenWidth)
            DateTextField(value = depMinute, onValueChange = {depMinute = it}, label = "分", screenWidth = screenWidth)
        }

        //目的地の入力欄
        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("目的地") }
        )

        //到着日時の入力欄
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            DateTextField(value = desYear, onValueChange = {desYear = it}, label = "年", screenWidth = screenWidth)
            DateTextField(value = desMonth, onValueChange = {desMonth = it}, label = "月", screenWidth = screenWidth)
            DateTextField(value = desDay, onValueChange = {desDay = it}, label = "日", screenWidth = screenWidth)
            DateTextField(value = desHour, onValueChange = {desHour = it}, label = "時", screenWidth = screenWidth)
            DateTextField(value = desMinute, onValueChange = {desMinute = it}, label = "分", screenWidth = screenWidth)
        }

        Text(text = "TODOリスト")

        OutlinedTextField(
            value = todoTitle,
            onValueChange = { todoTitle = it},
            label = { Text("TODO") }
        )

        //todoListにTODOを追加する
        Button(onClick = {
            if (todoTitle.isNotEmpty()){
                todoList = todoList.toMutableList().apply { add(todoTitle) }
                todoTitle = ""
            }
        }) {
            Text(text = "追加")
        }

        //TODOリストを表示
        LazyColumn() {
            items(todoList) { todo ->
                Row {
                    Text(text = todo, fontSize = 25.sp)
                    //TODOを削除する
                    Button(onClick = {
                        todoList = todoList.toMutableList().apply { remove(todo) }
                    }) {
                        Text(text = "削除")
                    }
                }
            }
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            //TravelListに移動
            Button(onClick = { navController.navigate("travellist") }) {
                Text(text = "戻る")
            }

            //ConfirmScheduleに移動。データ渡しは未実装
            Button(onClick = { navController.navigate("confirmschedule") }) {
                Text(text = "次へ")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewInputSchedule() {
    val navController = rememberNavController()
    InputSchedule(navController)
}