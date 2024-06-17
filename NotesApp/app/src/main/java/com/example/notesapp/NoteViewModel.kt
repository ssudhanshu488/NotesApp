package com.example.notesapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.NoteRepository
import com.example.notesapp.data.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteRepository: NoteRepository = Graph.noteRepository
):ViewModel() {
    var NotetitleState by mutableStateOf("")
    var NoteDescriptionState by mutableStateOf("")

    fun onNoteTitledChanged(newString:String){
        NotetitleState = newString
    }

    fun onNoteDescriptionState(newString: String){
        NoteDescriptionState = newString
    }

    lateinit var getAllNotes: Flow<List<Notes>>

    init {
        viewModelScope.launch {
            getAllNotes = noteRepository.getNotes()

        }
    }

    fun addNote(note:Notes){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.addAnote(note = note)
        }
    }

    fun getNoteById(id:Long): Flow<Notes> {
        return noteRepository.getANoteById(id)
    }

    fun updateNote(note:Notes){
        viewModelScope.launch(Dispatchers.IO) {

            noteRepository.updateNote(note = note)
        }
    }

    fun deleteNote(note:Notes){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteANote(note= note)
        }
    }

}