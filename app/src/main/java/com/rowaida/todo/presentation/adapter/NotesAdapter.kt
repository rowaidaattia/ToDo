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
import kotlinx.coroutines.runBlocking
import java.util.*


class NotesAdapter(private var notes: MutableList<Note>, val noteViewModel: NoteViewModel,
                   val notesInterface: NotesInterface, private val tabName: String?, private val username: String
) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    var mRecyclerView: RecyclerView? = null


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val status: CheckBox = itemView.findViewById(R.id.note_status)
        val note: TextView = itemView.findViewById(R.id.note_text)
        val description: TextView = itemView.findViewById(R.id.note_description)
        val statusText: TextView = itemView.findViewById(R.id.status_text)
        private val editButton: ImageButton = itemView.findViewById(R.id.edit_button)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)

        init {
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
            status.setOnCheckedChangeListener { _, _ ->
                val updateNote = notes[adapterPosition]
                val updateStatus = if (status.isChecked) {
                    Status.DONE
                } else {
                    Status.IN_PROGRESS
                }
                //FIXME move to diff method
                updateNoteStatus(updateNote, updateStatus)
            }
        }

        private fun updateNoteStatus(updateNote: Note, updateStatus: Status) {
            runBlocking {
                noteViewModel.updateNote(
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
                getUpdatedNotes(username)
            }
            //FIXME default value/named param
            notesInterface.updateFragment(notes = notes)
        }

        private fun deleteNote(deleteNote: Note) {
            runBlocking {
                noteViewModel.removeNote(
                    Note(
                        id = deleteNote.id,
                        username = deleteNote.username,
                        name = deleteNote.name,
                        description = deleteNote.description,
                        status = deleteNote.status,
                        owner = deleteNote.owner,
                        date = Calendar.getInstance().time
                    )
                )
                getUpdatedNotes(username)
            }
            notesInterface.updateFragment(null, notes)
        }

        private fun initializeDelete() {
            deleteButton.setOnClickListener {
                val deleteNote = notes[adapterPosition]
                val username = deleteNote.username
                //FIXME move to diff method
                deleteNote(deleteNote)
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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        //FIXME Scope functions
        val curNote = notes[position]
        viewHolder.let {
            it.status.isChecked = curNote.status == Status.DONE
            it.note.text = curNote.name
            it.description.text = curNote.description
            it.statusText.text = curNote.status.toString()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = notes.size

    fun update(updatedNotes: List<Note>) {
        notes = updatedNotes as MutableList<Note>
        notifyDataSetChanged()
    }

    //FIXME don't invoke database or network or any heavy operation form adapter you should make the activity to handle this code
    fun getUpdatedNotes(username: String) {
        if (tabName != null) {
            notes = notesInterface.getUpdatedNotes(username, tabName)
        }
        if (!mRecyclerView?.isComputingLayout!!)
        {
            // add your code here
            notifyDataSetChanged()
        }

    }

}


