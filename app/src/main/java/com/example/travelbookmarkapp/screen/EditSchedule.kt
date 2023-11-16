package com.example.travelbookmarkapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.travelbookmarkapp.ui_components.DateTextField
import com.example.travelbookmarkapp.ui_components.EditDateTextField
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSchedule(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val arguments = navBackStackEntry?.arguments

    var title by remember { mutableStateOf("") }
    var departure by remember { mutableStateOf("") }
    var depYear by remember { mutableStateOf("2023") }
    var depMonth by remember { mutableStateOf("") }
    var depDay by remember { mutableStateOf("") }
    var depHour by remember { mutableStateOf("") }
    var depMinute by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var desYear by remember { mutableStateOf("2023") }
    var desMonth by remember { mutableStateOf("") }
    var desDay by remember { mutableStateOf("") }
    var desHour by remember { mutableStateOf("") }
    var desMinute by remember { mutableStateOf("") }
    var todoTitle by remember { mutableStateOf("") }
    var todoList by remember { mutableStateOf(listOf<String>()) }

    //DetailScheduleから渡されたデータを取得
    val detTitle = arguments?.getString("title").toString()
    val detDeparture = arguments?.getString("departure").toString()
    val detDepYear = arguments?.getString("depYear").toString()
    val detDepMonth = arguments?.getString("depMonth").toString()
    val detDepDay = arguments?.getString("depDay").toString()
    val detDepHour = arguments?.getString("depHour").toString()
    val detDepMinute = arguments?.getString("depMinute").toString()
    val detDestination = arguments?.getString("destination").toString()
    val detDesYear = arguments?.getString("desYear").toString()
    val detDesMonth = arguments?.getString("desMonth").toString()
    val detDesDay = arguments?.getString("desDay").toString()
    val detDesHour = arguments?.getString("desHour").toString()
    val detDesMinute = arguments?.getString("desMinute").toString()
    val detTodoList = arguments?.getString("todoList").toString().split(", ")
    val documentID = arguments?.getString("documentID").toString()

    val screenWidth = LocalConfiguration.current.screenWidthDp

    //全ての入力欄が空でないかどうか
    var allNotEmpty = title.isNotBlank() && departure.isNotBlank() && depYear.isNotBlank() && depMonth.isNotBlank()
            && depDay.isNotBlank() && depHour.isNotBlank() && depMinute.isNotBlank() && destination.isNotBlank()
            && desYear.isNotBlank() && desMonth.isNotBlank() && desDay.isNotBlank() && desHour.isNotBlank() && desMinute.isNotBlank()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //旅行タイトル
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("タイトル：${detTitle}→") },
            singleLine = true
        )

        //出発地の入力欄
        OutlinedTextField(
            value = departure,
            onValueChange = { departure = it },
            label = { Text("出発地：${detDeparture}→") },
            singleLine = true
        )

        //出発日時の入力欄
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){

            EditDateTextField(value = depYear, onValueChange = {depYear = it}, label = "${detDepYear}→", screenWidth = screenWidth)
            EditDateTextField(value = depMonth, onValueChange = {depMonth = it}, label = "${detDepMonth}月→", screenWidth = screenWidth)
            EditDateTextField(value = depDay, onValueChange = {depDay = it}, label = "${detDepDay}日→", screenWidth = screenWidth)
            EditDateTextField(value = depHour, onValueChange = {depHour = it}, label = "${detDepHour}時→", screenWidth = screenWidth)
            EditDateTextField(value = depMinute, onValueChange = {depMinute = it}, label = "${detDepMinute}分→", screenWidth = screenWidth)
        }

        //目的地の入力欄
        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("目的地：${detDestination}→") },
            singleLine = true
        )

        //到着日時の入力欄
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            EditDateTextField(value = desYear, onValueChange = {desYear = it}, label = "${detDesYear}→", screenWidth = screenWidth)
            EditDateTextField(value = desMonth, onValueChange = {desMonth = it}, label = "${detDesMonth}月→", screenWidth = screenWidth)
            EditDateTextField(value = desDay, onValueChange = {desDay = it}, label = "${detDesDay}日→", screenWidth = screenWidth)
            EditDateTextField(value = desHour, onValueChange = {desHour = it}, label = "${detDesHour}時→", screenWidth = screenWidth)
            EditDateTextField(value = desMinute, onValueChange = {desMinute = it}, label = "${detDesMinute}分→", screenWidth = screenWidth)
        }
        Text(text = "TODOリスト編集前")
        LazyColumn() {
            items(detTodoList) { todo ->
                Text(text = "・$todo")
            }
        }

        Text(text = "新しいTODOリスト")

        OutlinedTextField(
            value = todoTitle,
            onValueChange = { todoTitle = it},
            label = { Text("TODO") },
            singleLine = true
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
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "戻る")
            }

            //ConfirmScheduleにデータを渡しつつ移動
            Button(onClick = {
                //日付や時刻が一桁の場合は0を追加する
                if (depMonth.length == 1) depMonth = "0$depMonth"
                if (depDay.length == 1) depDay = "0$depDay"
                if (depHour.length == 1) depHour = "0$depHour"
                if (depMinute.length == 1) depMinute = "0$depMinute"
                if (desMonth.length == 1) desMonth = "0$desMonth"
                if (desDay.length == 1) desDay = "0$desDay"
                if (desHour.length == 1) desHour = "0$desHour"
                if (desMinute.length == 1) desMinute = "0$desMinute"

                navController.navigate("confirmeditschedule/$title/$departure/$depYear/$depMonth/$depDay/$depHour/$depMinute/$destination/$desYear/$desMonth/$desDay/$desHour/$desMinute/${todoList.joinToString { it }}/${documentID}") },
                enabled = allNotEmpty
            ) {
                Text(text = "次へ")
            }
        }
    }
}
