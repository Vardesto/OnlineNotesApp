package com.example.onlinenotesapp.presentation.screens.notescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.onlinenotesapp.data.models.Note
import com.example.onlinenotesapp.utils.DatabaseManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteScreenViewModel: ViewModel() {

    private val _contentState = MutableStateFlow<NoteScreenState>(NoteScreenState.Loading)
    val contentState = _contentState.asStateFlow()

    var noteId: String = "init"


    fun getNote(id: String){
        if (noteId != id){
            noteId = id
            DatabaseManager.NOTES.child(id).addValueEventListener(object: ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    val note = snapshot.getValue(Note::class.java)
                    if (note != null)
                        _contentState.value = NoteScreenState.Success(note)
                    else
                        _contentState.value = NoteScreenState.Deleted
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
            _contentState.value = NoteScreenState.Loading
            val task = DatabaseManager.NOTES.child(id).get()
            task.addOnSuccessListener {
                val note = it.getValue(Note::class.java)
                if (note != null){
                    _contentState.value = NoteScreenState.Success(note)
                }
            }.addOnFailureListener{
                _contentState.value = NoteScreenState.Error(it.localizedMessage ?: "Error")
            }
        }

    }

    fun updateNoteText(text: String){
        DatabaseManager.NOTES.child(noteId).updateChildren(mapOf(Pair("text", text)))
    }

    fun updateNoteTitle(title: String){
        DatabaseManager.NOTES.child(noteId).updateChildren(mapOf(Pair("title", title)))
    }

}