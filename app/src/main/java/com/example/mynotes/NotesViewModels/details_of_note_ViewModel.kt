package com.example.mynotes.NotesViewModels


import android.app.Dialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.database.NoteDao
import com.example.mynotes.database.NoteDatabaseService
import com.example.mynotes.database.Notes
import com.example.mynotes.utilClass.choise_enum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class details_of_note_ViewModel @Inject constructor(val context: Context) : ViewModel() {

    val dataOfNote = MutableLiveData<Notes>()

    private lateinit var dao: NoteDao

    init {
        dao = NoteDatabaseService.invoke(context).noteDao()
    }


    fun GetOneNoteFromRoomDatabase(context: Context, id: Int) {

        runBlocking {
            dataOfNote.value = dao.getOneNoteFromid(id)

        }
    }

    fun selectFun(
        operation: choise_enum,
        title: String = "",
        category: String = "",
        context: String = ""
    ): Boolean {
        var return_for_fun = false
        when (operation) {
            choise_enum.delete -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dao.deleteOneNote(dataOfNote.value!!)
                    return_for_fun = true
                }

            }
            choise_enum.update -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val id = dataOfNote.value?.NoteId
                    val note = Notes(id!!, title, category, context)

                    dao.updateOneNote(note)

                    return_for_fun = true
                }

            }
        }
        return return_for_fun
    }

}