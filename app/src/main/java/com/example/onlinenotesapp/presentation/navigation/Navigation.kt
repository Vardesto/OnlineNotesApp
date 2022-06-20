package com.example.onlinenotesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.onlinenotesapp.presentation.screens.listscreen.ListScreen
import com.example.onlinenotesapp.presentation.screens.listscreen.ListScreenViewModel
import com.example.onlinenotesapp.presentation.screens.notescreen.NoteScreen
import com.example.onlinenotesapp.presentation.screens.notescreen.NoteScreenViewModel

@Composable
fun Navigation(navController: NavHostController) {
    val listScreenViewModel = ListScreenViewModel()
    val noteScreenViewModel = NoteScreenViewModel()
    NavHost(navController = navController, startDestination = Screen.ListScreen.route){
        composable(route = Screen.ListScreen.route){
            ListScreen(navController = navController, listScreenViewModel = listScreenViewModel)
        }
        composable(
            route = Screen.NoteScreen.route + "/{noteId}",
            arguments = listOf(
                navArgument("noteId"){
                    type = NavType.StringType
                    nullable = false
                }
            )
            ){
            NoteScreen(noteId = it.arguments?.getString("noteId")!!, navController = navController, noteScreenViewModel = noteScreenViewModel)
        }
    }
}