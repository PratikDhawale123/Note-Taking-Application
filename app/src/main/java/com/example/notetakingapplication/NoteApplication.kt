package com.example.notetakingapplication

import android.app.Application
import com.example.notetakingapplication.Repository.NoteRepository
import com.example.notetakingapplication.Room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication:Application() {

    //this step is after NoteDatabase default data part at that is after NoteDataabaseCallback part
    val applicationScope= CoroutineScope(SupervisorJob())

    // by lazy is just an property deligation used in kotlin , when use by lazy : an instance of database is created only when application is first  needed
    val database by lazy { NoteDatabase.getDatabase(this,applicationScope) }

    val repository by lazy { NoteRepository(database.getDao()) }

    // after this just go to manifest file and inside the application tag add the name:".NoteApplication" and thats it

    // now to chech our work till now , we will go to NoteDatabase class and create an function which will extend RoomDatabase.CallBack() method and inside if we will override the OnCreate() method to add default data

}