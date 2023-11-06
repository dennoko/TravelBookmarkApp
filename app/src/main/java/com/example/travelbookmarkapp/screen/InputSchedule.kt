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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputSchedule() {

    var title by remember { mutableStateOf("") }
    var departure by remember { mutableStateOf("") }
    var depYear by remember { mutableStateOf("") }
    var depMonth by remember { mutableStateOf("") }
    var depDay by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var desYear by remember { mutableStateOf("") }
    var desMonth by remember { mutableStateOf("") }
    var desDay by remember { mutableStateOf("") }
    var todoTitle by remember { mutableStateOf("") }
    var todoList by remember { mutableStateOf(mutableListOf("todo1", "todo2")) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("タイトル") }
        )

        OutlinedTextField(
            value = departure,
            onValueChange = { departure = it },
            label = { Text("出発地") }
        )

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ){
            OutlinedTextField(
                value = depYear,
                onValueChange = { depYear = it },
                label = { Text("年") },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = depMonth,
                onValueChange = { depMonth = it },
                label = { Text("月") },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = depDay,
                onValueChange = { depDay = it },
                label = { Text("日") },
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("目的地") }
        )

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            OutlinedTextField(
                value = desYear,
                onValueChange = { desYear = it },
                label = { Text("年") },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = desMonth,
                onValueChange = { desMonth = it },
                label = { Text("月") },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = desDay,
                onValueChange = { desDay = it },
                label = { Text("日") },
                modifier = Modifier.weight(1f)
            )
        }

        Text(text = "TODOリスト")

        OutlinedTextField(
            value = todoTitle,
            onValueChange = { todoTitle = it},
            label = { Text("TODO") }
        )

        Button(onClick = { /*TODOを追加*/ }) {
            Text(text = "追加")
        }

        LazyColumn() {
            items(todoList) { todo ->
                Row {
                    Text(text = todo, fontSize = 25.sp)
                    Button(onClick = { /*TODOを削除*/ }) {
                        Text(text = "削除")
                    }
                }
            }
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(onClick = { /*TravelListに移動*/ }) {
                Text(text = "戻る")
            }

            Button(onClick = { /*最終確認画面に移動*/ }) {
                Text(text = "次へ")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewInputSchedule() {
    InputSchedule()
}