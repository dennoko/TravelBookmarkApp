package com.example.travelbookmarkapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TestScreen(navController: NavController) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))

        // 画面遷移用のコード route と Text に遷移先の名前を入れる 名前はMainActivityで定義したもの
        Button(onClick = { navController.navigate("") }) {
            Text(text = "")
        }
    }
}