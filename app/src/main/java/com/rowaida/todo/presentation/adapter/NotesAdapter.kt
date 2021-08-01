package com.rowaida.todo.presentation.adapter

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
import com.rowaida.todo.utils.ToDoStrings
import kotlinx.coroutines.runBlocking
import java.util.*


class NotesAdapter(private var notes: MutableList<Note>, val viewModel: NoteViewModel,
                   val notesInterface: NotesInterface, private val tabName: String?, private val username: String
) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val status: CheckBox = itemView.findViewById(R.id.note_status)
        val note: TextView = itemView.findViewById(R.id.note_text)
        val description: TextView = itemView.findViewById(R.id.note_description)
        val statusText: TextView = itemView.findViewById(R.id.status_text)
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
                notesInterface.onEditNoteClicked(updateNote)
            }
        }

        private fun initializeStatus() {
            //FIXME for check box use onCheckChangeListener not on click listener
            status.setOnClickListener {
                val updateNote = notes[adapterPosition]
                val updateStatus = if (status.isChecked) {
                    Status.DONE
                } else {
                    Status.IN_PROGRESS
                }
                //FIXME move to diff method
                runBlocking {
                    viewModel.updateNote(
                        Note(
                            id = updateNote.id,
                            username = updateNote.username,
                            name = updateNote.name,
                            description = "Description",
                            status = updateStatus,
                            owner = updateNote.owner,
                            date = Calendar.getInstance().time
                        )
                    )
                }
                getUpdatedNotes(username)
                //FIXME default value/named param
                notesInterface.updateFragment(null, notes)
            }
        }

        private fun initializeDelete() {
            deleteButton.setOnClickListener {
                val deleteNote = notes[adapterPosition]
                val username = deleteNote.username
                //FIXME move to diff method
                runBlocking {
                    viewModel.removeNote(
                        Note(
                            id = deleteNote.id,
                            username = deleteNote.username,
                            name = deleteNote.name,
                            description= "Description",
                            status = deleteNote.status,
                            owner = deleteNote.owner,
                            date = Calendar.getInstance().time
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
                .inflate(R.layout.note_cardview, viewGroup, false)
        )

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        //FIXME Scope functions
        val curNote = notes[position]
        viewHolder.status.isChecked = curNote.status == Status.DONE
        viewHolder.note.text = curNote.name
        viewHolder.description.text = curNote.description
        viewHolder.statusText.text = curNote.status.toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = notes.size

    fun update(updatedNotes: List<Note>) {
        notes = updatedNotes as MutableList<Note>
        notifyDataSetChanged()
    }

    //FIXME don't invoke database or network or any heavy operation form adapter you should make the activity to handle this code
    fun getUpdatedNotes(username: String) {
        runBlocking {
            val notesNonMutable : List<Note> =
                when (tabName) {
                    ToDoStrings.get(R.string.myTasks) -> viewModel.getNotes(username)
                    ToDoStrings.get(R.string.subAccountTasks) -> viewModel.getSubAccountsNotes(username)
                    ToDoStrings.get(R.string.assignedTasks) -> viewModel.getAssignedNotes(username)
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


