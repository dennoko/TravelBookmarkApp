package com.example.travelbookmarkapp.ui_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TravelItem( txt: String, padding: Int = 0) {
    OutlinedCard(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding.dp)
    ) {
        Text(
            text = txt,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun PreviewTravelItem() {
    Surface {
        TravelItem(txt = "TravelItem",  padding = 4)
    }
}