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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ConfirmSchedule() {

    var title = ""
    var departure = ""
    var depYear = ""
    var depMonth = ""
    var depDay = ""
    var depHour = ""
    var depMinute = ""
    var destination = ""
    var desYear = ""
    var desMonth = ""
    var desDay = ""
    var desHour = ""
    var desMinute = ""
    var todoList = mutableListOf("todo1", "todo2")

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
            Button(onClick = { /*TravelListに移動*/ }) {
                Text(text = "やめる")
            }

            Button(onClick = { /*データを保存＆TravelListに移動*/ }) {
                Text(text = "登録")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewConfirmSchedule() {
    ConfirmSchedule()
}