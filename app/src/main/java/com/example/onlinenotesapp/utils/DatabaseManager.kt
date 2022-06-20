package com.example.onlinenotesapp.utils

import android.util.Log
import com.example.onlinenotesapp.data.models.Note
import com.example.onlinenotesapp.presentation.screens.listscreen.ListScreenState
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class DatabaseManager {

    companion object{

        private val mainReference = Firebase.database.reference

        val NOTES = mainReference.child("NOTES")
        val USERS = mainReference.child("USERS")

        fun addNote(): String{
            val id = UUID.randomUUID().toString()
            val note = Note(
                id,
                "",
                "",
                Date().time.toString(),
                Date().time.toString()
            )
            NOTES.child(id).setValue(note)
            return id
        }

    }
}