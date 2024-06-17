package com.example.notesapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addNote(noteEntity: Notes)

    @Query("Select * from `note-table`")
    abstract fun getAllNotes(): Flow<List<Notes>>

    @Update
    abstract suspend fun updateANote(noteEntity:Notes)

    @Delete
    abstract suspend fun deleteANote(noteEntity: Notes)

    @Query("Select * from `note-table` where id=:id")
    abstract fun getANotebyId(id:Long):Flow<Notes>
}