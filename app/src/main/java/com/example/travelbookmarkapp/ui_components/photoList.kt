package com.example.travelbookmarkapp.ui_components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.jetbrains.annotations.Async


@Composable
fun onClickPhotoList(uri: String) {
    Log.d("photoListUri", "$uri")
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .size(width = 240.dp, height = 100.dp)
    ) {
        Text(
            text = "Filled",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
        Log.d("card", "動いた")

        val uriList = uri.split(" ")
        Log.d("uriList", "$uriList")

        Column(){
            uriList.forEach() { uri ->
                AsyncImage(model = uri, contentDescription = null)
                Log.d("image", "動いた")
                Log.d("imagee", "$uri")
            }
        }
    }
}