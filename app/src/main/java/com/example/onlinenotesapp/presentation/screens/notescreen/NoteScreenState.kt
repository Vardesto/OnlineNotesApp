package com.example.onlinenotesapp.presentation.screens.notescreen

import com.example.onlinenotesapp.data.models.Note

sealed class NoteScreenState{
    object Loading: NoteScreenState()
    data class Error(val message: String): NoteScreenState()
    data class Success(val note: Note): NoteScreenState()
    object Deleted: NoteScreenState()
}
