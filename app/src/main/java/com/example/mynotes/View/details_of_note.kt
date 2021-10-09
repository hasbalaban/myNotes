package com.example.mynotes.View

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.mynotes.NotesViewModels.addFragmentViewModel
import com.example.mynotes.NotesViewModels.details_of_note_ViewModel
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentDetailsOfNoteBinding
import com.example.mynotes.enums.enum_delete_and_update
import com.example.mynotes.utilClass.choise_enum


class details_of_note : Fragment() {

    private lateinit var dataBindingOfDetails: FragmentDetailsOfNoteBinding
    private lateinit var detailsOfNoteViewmodel: details_of_note_ViewModel
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsOfNoteViewmodel = details_of_note_ViewModel(requireContext())
        dataBindingOfDetails =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details_of_note, container, false)

        setHasOptionsMenu(true)
        return dataBindingOfDetails.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setUp()

        super.onViewCreated(view, savedInstanceState)
    }

    //we start here function that in detailsOfNoteViewmodel
    fun setUp() {
        arguments?.let {
            id = it.getString("id")!!.toInt()
            detailsOfNoteViewmodel.GetOneNoteFromRoomDatabase(requireContext(), id!!)
            initialize()
        }


    }

    //to do procceses , when we start application
    fun initialize() {
        detailsOfNoteViewmodel.dataOfNote.observe(viewLifecycleOwner, Observer { note ->
            dataBindingOfDetails.notes = note
            val creatCategories = arrayListOf<String>()

            creatCategories.add("genel")
            creatCategories.add("özel")
            val adapter = ArrayAdapter(
                requireContext(), R.layout.support_simple_spinner_dropdown_item,
                creatCategories
            )
            dataBindingOfDetails.NoteCategory.adapter = adapter

            when (note.NoteCategory) {

                "genel" -> dataBindingOfDetails.NoteCategory.setSelection(0)
                "ozel" -> dataBindingOfDetails.NoteCategory.setSelection(1)
            }


        })

    }

    // initialize menu here
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.delete -> createrAlertDialog(
                "gerçekten silmek istiyor musunuz?",
                "Silme",
                enum_delete_and_update.delete
            )
            R.id.update -> createrAlertDialog(
                "gerçekten güncellemek istiyor musunuz?",
                "Güncelleme",
                enum_delete_and_update.update
            )
        }

        return super.onOptionsItemSelected(item)
    }

    // fun for note delete
    fun delete() {
        val secenek = choise_enum.delete
        Toast.makeText(requireContext(), "silindi", Toast.LENGTH_SHORT).show()

        detailsOfNoteViewmodel.selectFun(secenek)
        afterDelete()

    }

    // the procceses that to do after delete
    // return to feed fragment
    fun afterDelete(){
        val action = details_of_noteDirections.actionDetailsOfNoteToFeedFragment2()
        Navigation.findNavController(dataBindingOfDetails.root).navigate(action)
    }

    // fun for note update
    fun update() {

        val secenek = choise_enum.update

        val title = dataBindingOfDetails.NoteTitle.text.toString()
        var category = dataBindingOfDetails.NoteCategory.selectedItem.toString()
        val context = dataBindingOfDetails.NoteContext.text.toString()
        if (category == "özel")
            category = "ozel"

        detailsOfNoteViewmodel.selectFun(secenek, title, category, context)
        Toast.makeText(requireContext(), "güncellendi", Toast.LENGTH_SHORT).show()
    }

    // we create a alert dialog her for update and delete , too
    fun createrAlertDialog(mesaj: String, title: String, operation: enum_delete_and_update) {


        val alertDialog = AlertDialog.Builder(requireContext())
            .setMessage(mesaj)
            .setTitle(title)
            .setNegativeButton("Hayır") { dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(requireContext(), "$title işlemi iptal edildi", Toast.LENGTH_SHORT)
                    .show()
            }


        when (operation) {
            enum_delete_and_update.delete -> {

                alertDialog.setPositiveButton("Evet") { dialogInterface: DialogInterface, i: Int ->
                    delete()
                }
                alertDialog.show()
            }
            enum_delete_and_update.update -> {
                alertDialog.setPositiveButton("Evet") { dialogInterface: DialogInterface, i: Int ->
                    update()
                }
                alertDialog.show()

            }
        }


    }


}


