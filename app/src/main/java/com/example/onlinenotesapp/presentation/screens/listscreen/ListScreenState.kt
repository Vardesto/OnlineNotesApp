package com.example.onlinenotesapp.presentation.screens.listscreen

import com.example.onlinenotesapp.data.models.Note

sealed class ListScreenState {
    data class Success(val list: List<Note>): ListScreenState()
    data class Error(val message: String): ListScreenState()
    object Nothing: ListScreenState()
    object Loading: ListScreenState()
}