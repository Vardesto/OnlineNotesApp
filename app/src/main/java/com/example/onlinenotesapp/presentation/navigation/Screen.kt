package com.example.onlinenotesapp.presentation.navigation

sealed class Screen(val route: String) {
    object ListScreen: Screen("list_screen")
    object NoteScreen: Screen("note_screen")
}