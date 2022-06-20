package com.example.onlinenotesapp.presentation.screens.listscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.onlinenotesapp.data.models.Note

@Composable
fun ListScreenItem(note: Note, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .padding(10.dp)
        .clickable { onClick() },
        elevation = 10.dp,
    ) {
        Text(note.title, modifier = Modifier.padding(10.dp))
    }
}