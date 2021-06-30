package com.rowaida.todo.presentation.adapter

import android.view.*
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status
import com.rowaida.todo.presentation.activity.NotesActivity
import com.rowaida.todo.presentation.viewModel.NoteViewModel
import com.rowaida.todo.utils.Constants
import kotlinx.coroutines.runBlocking


class NotesAdapter(private var notes: MutableList<Note>,
                   val viewModel: NoteViewModel, val notesActivity: NotesActivity
) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val status: CheckBox = itemView.findViewById(R.id.note_status)
        val note: TextView = itemView.findViewById(R.id.note_text)
        private val button: ImageButton = itemView.findViewById(R.id.delete_button)

        init {
            initializeNote()
            initializeStatus()
            initializeDelete()
        }

        private fun initializeNote() {
            note.setOnClickListener {
                val updateNote = notes[adapterPosition]
                val alert = AlertDialog.Builder(notesActivity)
                val edittext = EditText(notesActivity.applicationContext)
                edittext.setText(updateNote.note)
                alert.setMessage("Edit Your Note")
                alert.setView(edittext)
                alert.setPositiveButton(
                    Constants.update
                ) { _, _ -> //What ever you want to do with the value

                    runBlocking {
                        viewModel.updateNote(
                            Note(
                                id = updateNote.id,
                                username = updateNote.username,
                                note = edittext.text.toString(),
                                status = updateNote.status
                            )
                        )
                    }
                    getUpdatedNotes(updateNote.username)

                }

                alert.setNegativeButton(
                    Constants.cancel
                ) { dialog, _ ->
                    dialog.dismiss()
                }

                alert.show()
            }
        }

        private fun initializeStatus() {
            status.setOnClickListener {
                val updateNote = notes[adapterPosition]
                val updateStatus = if (status.isChecked) {
                    Status.DONE
                } else {
                    Status.IN_PROGRESS
                }
                runBlocking {
                    viewModel.updateNote(
                        Note(
                            id = updateNote.id,
                            username = updateNote.username,
                            note = updateNote.note,
                            status = updateStatus
                        )
                    )
                }
                getUpdatedNotes(updateNote.username)
            }
        }

        private fun initializeDelete() {
            button.setOnClickListener {
                val deleteNote = notes[adapterPosition]
                val username = deleteNote.username
                runBlocking {
                    viewModel.removeNote(
                        Note(
                            id = deleteNote.id,
                            username = deleteNote.username,
                            note = deleteNote.note,
                            status = deleteNote.status
                        )
                    )
                }
                getUpdatedNotes(username)
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

    fun getUpdatedNotes(username: String) {
        runBlocking {
            val notesNonMutable = viewModel.getNotes(username)
            notes = if (notesNonMutable.isEmpty()) {
                mutableListOf()
            } else {
                notesNonMutable as MutableList<Note>
            }
            notifyDataSetChanged()
        }
    }

}


