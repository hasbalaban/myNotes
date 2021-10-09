package com.example.mynotes.database

import androidx.room.*

@Dao
interface NoteDao {

    @Query("select * from notes ")
    suspend fun getAllNotes(): List<Notes>

    @Query("select * from notes where NoteCategory =:NoteCategory ")
    suspend fun getNotesFromCategory(NoteCategory: String): List<Notes>

    @Query("select * from notes where NoteId =:id ")
    suspend fun getOneNoteFromid(id: Int): Notes

    @Query("select * from notes ")
    suspend fun getAllCategory(): List<Notes>



    @Insert
    suspend fun AddNoteToDatabase(note: Notes): Long


    @Delete
    suspend fun deleteOneNote (note:Notes)

    @Update
    suspend fun updateOneNote (note: Notes)

}