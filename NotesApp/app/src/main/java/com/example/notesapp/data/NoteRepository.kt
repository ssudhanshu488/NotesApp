package com.example.notesapp.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    suspend fun addAnote(note:Notes){
        noteDao.addNote(note)
    }

    fun getNotes():Flow<List<Notes>> = noteDao.getAllNotes()

    fun getANoteById(id:Long): Flow<Notes> {
        return noteDao.getANotebyId(id)
    }

    suspend fun updateNote(note:Notes){
        noteDao.updateANote(note)
    }

    suspend fun deleteANote(note:Notes){
        noteDao.deleteANote(note)
    }
}