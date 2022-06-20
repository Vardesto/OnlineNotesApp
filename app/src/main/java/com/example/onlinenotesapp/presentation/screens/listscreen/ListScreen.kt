package com.example.onlinenotesapp.presentation.screens.listscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.onlinenotesapp.presentation.navigation.Screen
import com.example.onlinenotesapp.utils.DatabaseManager

@Composable
fun ListScreen(
    navController: NavHostController,
    listScreenViewModel: ListScreenViewModel
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            val id = DatabaseManager.addNote()
            navController.navigate(Screen.NoteScreen.route + "/$id") },
            Modifier.padding(20.dp)) { Icon(Icons.Filled.Add, "") } },
        content = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                val contentState by listScreenViewModel.contentState.collectAsState()
                when(contentState){
                    is ListScreenState.Loading -> CircularProgressIndicator()
                    is ListScreenState.Nothing -> Text("Nothing")
                    is ListScreenState.Success -> {
                        val data = (contentState as ListScreenState.Success).list
                        LazyColumn(modifier = Modifier.fillMaxSize()){
                            items(data.size) {
                                ListScreenItem(note = data[it]){
                                    navController.navigate(Screen.NoteScreen.route + "/${data[it].id}")
                                }
                            }
                        }
                    }
                    is ListScreenState.Error -> Text((contentState as ListScreenState.Error).message)
                }
            }
    })
    LaunchedEffect(key1 = true){
        if (listScreenViewModel.contentState.value !is ListScreenState.Success){
            listScreenViewModel.getExistNotes()
        }
    }



}