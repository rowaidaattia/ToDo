package com.rowaida.todo.presentation

import android.view.*
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status
import kotlinx.coroutines.runBlocking


class NotesAdapter(private var notes: MutableList<Note>, val viewModel: NoteViewModel) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val status: CheckBox = itemView.findViewById(R.id.note_status)
        val note: TextView = itemView.findViewById(R.id.note_text)
        private val button: ImageButton = itemView.findViewById(R.id.delete_button)

        init {
            button.setOnClickListener {
                //delete button clicked
                val deleteNote = notes[adapterPosition]
                val username = deleteNote.username
                runBlocking {
                    viewModel.removeNote(Note(
                        id = deleteNote.id,
                        username = deleteNote.username,
                        note = deleteNote.note,
                        status = deleteNote.status
                    ))
                    notes = viewModel.getNotes(username) as MutableList<Note>
                    println("NOTES: $notes")
                    notifyDataSetChanged()
                }
                println("DELETE BUTTON CLICKED")
            }
        }
    }



    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item

        return ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.note_layout, viewGroup, false)
        )

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        val curNote = notes[position]
        viewHolder.status.isChecked = curNote.status == Status.DONE
        viewHolder.note.text = curNote.note
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = notes.size

    fun update(updatedNotes: List<Note>) {
        notes.clear()
        notes.addAll(updatedNotes)
        notifyDataSetChanged()
    }

}


