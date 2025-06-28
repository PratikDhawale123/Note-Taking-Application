package com.example.notetakingapplication.Room
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notetakingapplication.Model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {

    // Insert a single note, make it a suspend function
    @Insert
    suspend fun insert(note: Note)

    // Update a single note, make it a suspend function
    @Update
    suspend fun update(note: Note)

    // Delete a single note, make it a suspend function
    @Delete
    suspend fun delete(note: Note)

    // Query to get all notes as a Flow
    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllNotes(): Flow<List<Note>>

    // Query to delete all notes
    @Query("DELETE FROM note_table")
    suspend fun deleteAllNote():Int
}
