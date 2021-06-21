package com.rowaida.todo.presentation

import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status

class NotesAdapter(private val notes: Array<Note>) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val status: CheckBox = view.findViewById(R.id.note_status)
        val note: TextView = view.findViewById(R.id.note_text)
        //val delete: ImageButton = view.findViewById(R.id.delete_button)
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

}


