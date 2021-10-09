package com.example.mynotes.View

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.mynotes.NotesViewModels.addFragmentViewModel
import com.example.mynotes.R
import com.example.mynotes.clickListeners.ClickListener
import com.example.mynotes.database.NoteDatabaseService
import com.example.mynotes.database.Notes
import com.example.mynotes.databinding.FragmentAddBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class addFragment : Fragment() {


    lateinit var dataBindingFragment: FragmentAddBinding
    lateinit var addFragmentViewModel: addFragmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        dataBindingFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)

        openAnimetor()
        addFragmentViewModel = addFragmentViewModel(requireContext())

        getAllCategories()


        return dataBindingFragment.root

    }

    //get all categories and set spinner
    fun getAllCategories() {
        val a = arrayListOf<String>()

        a.add("genel")
        a.add("özel")
        val adapter = ArrayAdapter<String>(
            requireContext(), R.layout.support_simple_spinner_dropdown_item,
            a
        )
        dataBindingFragment.NoteCategory.adapter = adapter


    }

    fun openAnimetor() {

    }


    //after you click save button
    fun DoAfterClick() {

        val title = dataBindingFragment.NoteTitle.text.toString()
        var category = dataBindingFragment.NoteCategory.selectedItem.toString()
        val context = dataBindingFragment.NoteContext.text.toString()

        if (category == "özel")
            category = "ozel"
        createAlertDialog(
            "Notu kaydetmek istediğinize emin misiniz?", "Not Ekleme",
            title,category,context
            )


    }

    // show alert dialog and save a note
    fun createAlertDialog(
        mesaj: String,
        title: String,
        titleOfNote: String,
        category: String,
        context: String
    ) {

        val alertDialog = AlertDialog.Builder(requireContext())
            .setMessage(mesaj)
            .setTitle(title)
            .setNegativeButton("Hayır") { dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(requireContext(), "$title işlemi iptal edildi", Toast.LENGTH_SHORT)
                    .show()
            }
        alertDialog.setPositiveButton("Evet") { dialogInterface: DialogInterface, i: Int ->
            var savedId = addFragmentViewModel.saveNoteToNotesDatabase(titleOfNote, category, context)

            try {
                Toast.makeText(requireContext(), savedId.toString(), Toast.LENGTH_LONG).show()
                afterSaved()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        alertDialog.show()
    }

    // after save some animate and show icon of saved then hide it.
    fun afterSaved() {


        dataBindingFragment.succesed.visibility = View.VISIBLE
        var savedAnimator = dataBindingFragment.succesed.animate()
        val lastLocation = dataBindingFragment.succesed.rotation
        savedAnimator.alpha(0F).duration = 0L
        savedAnimator.alpha(1F).duration = 800L
        savedAnimator.rotation(360F + lastLocation).duration = 800L
        savedAnimator.start()
        val a = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

                dataBindingFragment.succesed.visibility = View.GONE
            }

        }.start()


        dataBindingFragment.NoteTitle.setText("")
        dataBindingFragment.NoteContext.setText("")
    }


    //navigate your option
    fun navigater(view: View, action: NavDirections) {


        Navigation.findNavController(view).navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //when you click menu item find it and make that procces
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                DoAfterClick()
            }
        }

        return super.onOptionsItemSelected(item)
    }


}