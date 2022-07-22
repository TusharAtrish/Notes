package com.example.notesapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NoteRVAdapter(
    val context: Context,
    val noteClickListener: NoteClickListener
) : RecyclerView.Adapter<NoteRVAdapter.NotesViewHolder>() {

    private val allNotes = ArrayList<Note>()

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTV: TextView = itemView.findViewById(R.id.TVNote)
        val timeTV: TextView = itemView.findViewById(R.id.idTVTimeStamp)
        val deleteTV: ImageView = itemView.findViewById(R.id.idIVDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_rv_item, parent, false)
        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.noteTV.text = currentNote.noteTitle
        holder.timeTV.text = currentNote.timeStamp
        holder.deleteTV.setOnClickListener {
            noteClickListener.onDeleteIconClick(currentNote)
        }
        holder.itemView.setOnClickListener {
            noteClickListener.onNoteClick(currentNote)
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()

    }

    interface NoteClickListener {
        fun onNoteClick(note: Note)
        fun onDeleteIconClick(note: Note)
    }
}
