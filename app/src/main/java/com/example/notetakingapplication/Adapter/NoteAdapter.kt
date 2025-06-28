package com.example.notetakingapplication.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notetakingapplication.Model.Note
import com.example.notetakingapplication.R
import com.example.notetakingapplication.View.MainActivity
import com.example.notetakingapplication.View.UpdateActivity

class NoteAdapter(private val activity: MainActivity) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notes: List<Note> = ArrayList()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_Title: TextView = itemView.findViewById(R.id.TV_Title)
        val item_Description: TextView = itemView.findViewById(R.id.TV_description)
        val cardView: CardView = itemView.findViewById(R.id.cardview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_items, parent, false)

        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNotes: Note = notes[position]

        holder.item_Title.text = currentNotes.Title
        holder.item_Description.text = currentNotes.Description

        holder.cardView.setOnClickListener {
            val intent = Intent(activity, UpdateActivity::class.java)
            intent.putExtra("currentTitle", currentNotes.Title)
            intent.putExtra("currentDescription", currentNotes.Description)
            intent.putExtra("currentId", currentNotes.id)

            //activityResultLauncher;

            activity.updateActivityResultLauncher.launch(intent)
        }
    }

    // Renamed setNotes to updateNotes to avoid the clash
    fun updateNotes(myNotes: List<Note>) {
        this.notes = myNotes

        // this notifyDatasetChanged() method will alert the adapter if there is any data change
        notifyDataSetChanged()
    }

    fun getNote(position: Int): Note {
        return notes[position]

    }
}