package com.example.mynotes.NotesRecyclerViewAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.View.feedFragmentDirections
import com.example.mynotes.database.Notes
import com.example.mynotes.databinding.NotesRecyclerItemBinding

class NotesRecyclerViewAdapter(val NotesList :List<Notes>) : RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {



    class ViewHolder( var view: NotesRecyclerItemBinding) : RecyclerView.ViewHolder(view.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<NotesRecyclerItemBinding>(inflater, R.layout.notes_recycler_item,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return NotesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.view.notes = NotesList[position]
        holder.view.itemOfRecycler.setOnClickListener {
            val id = NotesList.get(position).NoteId.toString()
            val action = feedFragmentDirections.actionFeedFragmentToDetailsOfNote(id)
            Navigation.findNavController(holder.view.root).navigate(action)

        }
    }
}