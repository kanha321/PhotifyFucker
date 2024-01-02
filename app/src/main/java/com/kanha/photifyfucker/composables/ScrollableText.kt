package com.kanha.photifyfucker.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times

@Composable
fun ScrollableText(
    output: String,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .border(2.dp, Color.Gray)
//            .heightIn(max = 15 * 20.dp) // 20dp is the default line height
            .background(Color.Black)
            .padding(16.dp)
            .then(modifier),
        reverseLayout = true // Scroll to bottom by default
    ) {
        item {
            Text(
                text = output,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp
            )
        }
    }
}