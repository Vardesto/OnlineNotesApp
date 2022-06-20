package com.example.onlinenotesapp.presentation.screens.notescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun NoteScreenDeletedDialog(popBack: () -> Unit) {
    AlertDialog(onDismissRequest = { popBack() },
        text = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                Text(text = "This note was deleted")
            }

        },
        buttons = {
            Row(modifier = Modifier.padding(10.dp)) {
                Button(onClick = { popBack() }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "OK")
                }
            }

        }
    )
}