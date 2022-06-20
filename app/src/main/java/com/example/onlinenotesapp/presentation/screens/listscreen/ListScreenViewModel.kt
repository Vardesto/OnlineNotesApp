package com.example.onlinenotesapp.presentation.screens.listscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.onlinenotesapp.base.BaseChildEventListener
import com.example.onlinenotesapp.data.models.Note
import com.example.onlinenotesapp.utils.DatabaseManager
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListScreenViewModel: ViewModel() {

    private val _contentState = MutableStateFlow<ListScreenState>(ListScreenState.Loading)
    val contentState = _contentState.asStateFlow()

    fun getExistNotes(){
        _contentState.value = ListScreenState.Loading
        val task = DatabaseManager.NOTES.get()
        val container = mutableListOf<Note>()
        task.addOnSuccessListener {
            it.children.forEach { data ->
                val note = data.getValue(Note::class.java)
                if (note != null){
                    container.add(note)
                }
            }
            if (container.size > 0){
                _contentState.value = ListScreenState.Success(container)
            } else {
                _contentState.value = ListScreenState.Nothing
            }
        }.addOnFailureListener{
            _contentState.value = ListScreenState.Error(it.localizedMessage ?: "Error")
        }
        DatabaseManager.NOTES.addChildEventListener(object: BaseChildEventListener{

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                when(_contentState.value){
                    is ListScreenState.Success -> {
                        val notes = (_contentState.value as ListScreenState.Success).list.toMutableList()
                        notes.add(snapshot.getValue(Note::class.java)!!)
                        _contentState.value = ListScreenState.Success(notes)
                    }
                    is ListScreenState.Loading -> {

                    }
                    else -> {
                        _contentState.value = ListScreenState.Success(listOf(snapshot.getValue(Note::class.java)!!))
                    }
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.e("changed", "changed")
                when(_contentState.value){
                    is ListScreenState.Success -> {
                        val notes = (_contentState.value as ListScreenState.Success).list.toMutableList()
                        val changed = snapshot.getValue(Note::class.java)!!
                        notes[notes.indexOf(notes.find { it.id ==  changed.id })] = changed
                        _contentState.value = ListScreenState.Success(notes)
                    }
                    is ListScreenState.Loading -> {

                    }
                    else -> {
                        _contentState.value = ListScreenState.Success(listOf(snapshot.getValue(Note::class.java)!!))
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.e("remove", "remove")
                super.onChildRemoved(snapshot)
                when(_contentState.value){
                    is ListScreenState.Success -> {
                        val notes = (_contentState.value as ListScreenState.Success).list.toMutableList()
                        val removed = snapshot.getValue(Note::class.java)!!
                        notes.remove(removed)
                        if (notes.size == 0){
                            _contentState.value = ListScreenState.Nothing
                        } else {
                            _contentState.value = ListScreenState.Success(notes)
                        }

                    }
                    else -> {}
                }
            }

        }
        )
    }

}