package com.example.travelbookmarkapp.ui_components

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.travelbookmarkapp.ui.theme.TravelBookmarkAppTheme

// Button の関数はラムダ式で受け取る
@Composable
fun DefaultButton(txt: String, todo : () -> Unit) {
    Button(
        onClick = { todo() },
        modifier = Modifier
            .padding(4.dp),
        shape = RoundedCornerShape(4.dp)
        ) {
        Text(text = txt)
    }
}

@Preview
@Composable
fun DefaultButtonPreview() {
    TravelBookmarkAppTheme {
        Surface {
            DefaultButton(txt = "Button", todo = { Toast.makeText(null, "Button", Toast.LENGTH_SHORT).show() })
        }
    }
}