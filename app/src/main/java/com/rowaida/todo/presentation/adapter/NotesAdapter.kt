package com.rowaida.todo.presentation.adapter

import android.content.res.Resources
import android.view.*
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status
import com.rowaida.todo.presentation.activity.NotesInterface
import com.rowaida.todo.presentation.viewModel.NoteViewModel
import kotlinx.coroutines.runBlocking


class NotesAdapter(private var notes: MutableList<Note>, val viewModel: NoteViewModel,
                   val notesInterface: NotesInterface, private val tabName: String?, private val username: String
) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val status: CheckBox = itemView.findViewById(R.id.note_status)
        val note: TextView = itemView.findViewById(R.id.note_text)
        private val editButton: ImageButton = itemView.findViewById(R.id.edit_button)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)

        init {
            //initializeNote()
            initializeStatus()
            initializeEdit()
            initializeDelete()
        }

        private fun initializeEdit() {
            editButton.setOnClickListener {
                val updateNote = notes[adapterPosition]
                notesInterface.editNote(updateNote)
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
                            status = updateStatus,
                            owner = updateNote.owner
                        )
                    )
                }
                getUpdatedNotes(username)
                notesInterface.updateFragment(null, notes)
            }
        }

        private fun initializeDelete() {
            deleteButton.setOnClickListener {
                val deleteNote = notes[adapterPosition]
                val username = deleteNote.username
                runBlocking {
                    viewModel.removeNote(
                        Note(
                            id = deleteNote.id,
                            username = deleteNote.username,
                            note = deleteNote.note,
                            status = deleteNote.status,
                            owner = deleteNote.owner
                        )
                    )
                }
                getUpdatedNotes(username)
                notesInterface.updateFragment(null, notes)
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
        notes = updatedNotes as MutableList<Note>
        notifyDataSetChanged()
    }

    fun getUpdatedNotes(username: String) {
        runBlocking {
            val notesNonMutable : List<Note> =
                when (tabName) {
                    Resources.getSystem().getString(R.string.myTasks) -> viewModel.getNotes(username)
                    Resources.getSystem().getString(R.string.subAccountTasks) -> viewModel.getSubAccountsNotes(username)
                    Resources.getSystem().getString(R.string.assignedTasks) -> viewModel.getAssignedNotes(username)
                    else -> listOf()
                }
            //val notesNonMutable = viewModel.getNotes(username)
            notes = if (notesNonMutable.isEmpty()) {
                mutableListOf()
            } else {
                notesNonMutable as MutableList<Note>
            }
            notifyDataSetChanged()
        }
    }

}


