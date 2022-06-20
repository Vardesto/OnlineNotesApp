package com.example.onlinenotesapp.presentation.screens.notescreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.example.onlinenotesapp.utils.DatabaseManager
import java.util.*

@Composable
fun NoteScreen(
    noteId: String,
    noteScreenViewModel: NoteScreenViewModel,
    navController: NavHostController
) {
    val pageState by noteScreenViewModel.contentState.collectAsState()
    LaunchedEffect(key1 = true) {
        noteScreenViewModel.getNote(noteId)
    }
    Scaffold(content = {
        Column(modifier = Modifier.fillMaxSize()) {
            when (pageState) {
                is NoteScreenState.Success -> {
                    val note = (pageState as NoteScreenState.Success).note
                    TextField(
                        value = note.title,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            noteScreenViewModel.updateNoteTitle(it)
                        })
                    TextField(
                        value = note.text,
                        modifier = Modifier.fillMaxSize(),
                        onValueChange = {
                            noteScreenViewModel.updateNoteText(it)
                        })
                }
                is NoteScreenState.Error -> {
                    Text(text = (pageState as NoteScreenState.Error).message)
                }
                NoteScreenState.Loading -> {
                    CircularProgressIndicator()
                }
                NoteScreenState.Deleted -> {
                    NoteScreenDeletedDialog{
                        navController.popBackStack()
                    }
                }
            }
        }
    },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                DatabaseManager.NOTES.child(noteId).removeValue()
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.Delete, "")
            }
        }
    )


}