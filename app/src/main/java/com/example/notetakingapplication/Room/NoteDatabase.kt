package com.example.notetakingapplication.Room

import android.content.Context
import androidx.collection.intSetOf
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notetakingapplication.Model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch


//this class must be an abstract class
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase:RoomDatabase() {

    abstract  fun getDao():NoteDAO

    //singleton structure(preventing the creation of more than one object therefore we use singleton structure)
    companion object{
        // makes the instance of this class visible to all the other threads
        @Volatile
        private var INSTANCE:NoteDatabase?=null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context, scope: CoroutineScope):NoteDatabase{
            // if there is an instancce of a class return this instance and if there is no instance of class then create an instance and return the instance

            // ?: -> this is called elvis operator this means if instance is not null it returns the instance and if it is null it will run the code after ?:
            // syncronized : if more than one thread tries to create an instance at the same time then it will block them means (one thread is initialized at a time)
            return INSTANCE?: synchronized(this){
                val instance=Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).addCallback(NoteDatabaseCallback(scope))
                    //now since we have new parameter to getDatabase function we have to specify this to NoteApplication class
                    .build()

                INSTANCE=instance
                instance
            }
        }


    }

    //we are adding default data for our database by overriding the oncreate function
    private class NoteDatabaseCallback(private val scope: CoroutineScope):RoomDatabase.Callback(){

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let {database ->
                 // now we can add data to database

                // but the Room doesnt allow the below technique
                //database.getDao().insert(Note("t","e"))
                // so to do this we must use coroutin scope kotlin , therefore create constructor in NoteDatabaseCallback class

                scope.launch {
                    // now we will create an variable to access all the functions in DAO

                    val noteDao=database.getDao()
                   noteDao.insert(Note("title1","Description1"))
                   noteDao.insert(Note("title2","Description2"))
                   noteDao.insert(Note("title3","Description3"))

                    //after adding the default data , we can add this to database from function getDatabase just after the syncronized part by calling .callback() function

                    //now after this we cant directly see that the data is inserted so we now have to create an ui for it .
                }
            }
        }
    }

}