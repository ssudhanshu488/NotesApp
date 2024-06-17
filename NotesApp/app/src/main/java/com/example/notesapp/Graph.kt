package com.example.notesapp

import android.content.Context
import androidx.room.Room
import com.example.notesapp.data.NoteDatabase
import com.example.notesapp.data.NoteRepository

object Graph {
    lateinit var database:NoteDatabase

    val noteRepository by lazy{
        NoteRepository(noteDao = database.noteDao())

    }

    fun provide(context: Context){
        database= Room.databaseBuilder(context,NoteDatabase::class.java,"notelist.db").build()
    }
}