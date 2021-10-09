package com.example.mynotes.NotesViewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynotes.database.NoteDatabaseService
import com.example.mynotes.database.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class addFragmentViewModel(
    var context: Context
) : ViewModel() {


    var id = 0L
    val NotesLiveData = MutableLiveData<List<Notes>>()
    val NoteLiveData = MutableLiveData<List<Notes>>()

    fun saveNoteToNotesDatabase(
        NoteTitle: String,
        NoteCategory: String,
        NoteContext: String
    ): Long {
        val note =
            Notes(NoteTitle = NoteTitle, NoteCategory = NoteCategory, NoteContext = NoteContext)


        runBlocking {

            id = NoteDatabaseService(context).noteDao().AddNoteToDatabase(note)
        }
        return id

    }

    fun getCatoriesFromDatabase(): List<Notes> {
        var categories: List<Notes>
        runBlocking {

            categories = NoteDatabaseService(context).noteDao().getAllCategory()

        }
        return categories
    }


}