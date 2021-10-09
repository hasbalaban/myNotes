package com.example.mynotes.NotesViewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynotes.database.NoteDatabaseService
import com.example.mynotes.database.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class FeedFragmentViewModel(

    var context: Context
) : ViewModel() {


    val NotesLiveData = MutableLiveData<List<Notes>>()

    fun LoadingNotesFromNotesDatabase(category:String) {
        runBlocking {


            val dao = NoteDatabaseService.invoke(context).noteDao()

            NotesLiveData.value = dao.getNotesFromCategory(category)

        }

    }

    fun LoadingPrivateNotesFromNotesDatabase() {
        runBlocking {


            val dao = NoteDatabaseService.invoke(context).noteDao()

            NotesLiveData.value = dao.getNotesFromCategory("ozel")

        }

    }

}