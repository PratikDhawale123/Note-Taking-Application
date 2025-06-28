package com.example.notetakingapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
 data class Note(val Title:String,val Description:String) {
    @PrimaryKey(autoGenerate = true)
    var id=0
}