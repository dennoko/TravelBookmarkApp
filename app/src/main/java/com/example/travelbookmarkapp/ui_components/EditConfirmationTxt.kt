package com.example.travelbookmarkapp.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditConfirmationTxt(title: String, value: String) {
    Row(modifier = Modifier.padding(8.dp)) {
        Text(
            text = title,
            fontSize = 20.sp,
            modifier = Modifier
                .background(color = Color.Green, shape = RoundedCornerShape(4.dp))
                .padding(end = 4.dp)
            )
        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = value,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(end = 4.dp)
        )
    }
}

@Preview
@Composable
fun PreviewEditConfirmationTxt() {
    Surface {
        EditConfirmationTxt(title = "タイトル", value = "値")
    }
}

