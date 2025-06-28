package com.example.notetakingapplication.Repository

import androidx.annotation.WorkerThread
import com.example.notetakingapplication.Model.Note
import com.example.notetakingapplication.Room.NoteDAO
import kotlinx.coroutines.flow.Flow


//Repository's Task is to establish the connection between different data sources and the rest of application
class NoteRepository (private val noteDao:NoteDAO){


    // we'll get all the notes(data ) in the database
    val MyAllNotes:Flow<List<Note>> = noteDao.getAllNotes()

    @WorkerThread
    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    @WorkerThread
    suspend fun update(note: Note){
        noteDao.update(note)
    }


    @WorkerThread
    suspend fun delete(note: Note){
        noteDao.delete(note)
    }


    @WorkerThread
    suspend fun deleteAllNotes(){
        noteDao.deleteAllNote()
    }


    /* Remember our goal is to create only one instance of both the database and repository there is an easier way
    just  create an new class that inheriets from application , then then can be just retrieved from application  whenever needed  rather than constructed everytime

    therefore create a class NoteApplication and inherit the application class
    */


}