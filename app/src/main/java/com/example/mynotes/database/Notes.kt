package com.example.mynotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "NoteId")
    var NoteId: Int=0 ,

    @ColumnInfo(name = "NoteTitle")
      val NoteTitle: String ,

    @ColumnInfo(name = "NoteCategory")
      val NoteCategory: String ,


    @ColumnInfo(name = "NoteContext")
      val NoteContext: String
)

