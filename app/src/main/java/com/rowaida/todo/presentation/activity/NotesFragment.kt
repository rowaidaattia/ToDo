package com.rowaida.todo.presentation.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status
import com.rowaida.todo.framework.ToDoViewModelFactory
import com.rowaida.todo.presentation.adapter.NotesAdapter
import com.rowaida.todo.presentation.viewModel.NoteViewModel
import com.rowaida.todo.utils.ToDoConstants
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

class NotesFragment : Fragment() {

    lateinit var username : String
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var notesAdapter : NotesAdapter
    private lateinit var notes : List<Note>
    private lateinit var fragmentView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentView = inflater.inflate(
            R.layout.layout_notes, container, false
        )

        //initializeButton()

        username = arguments?.getString(ToDoConstants.username).toString()

        noteViewModel = ViewModelProvider(this, ToDoViewModelFactory)
            .get(NoteViewModel::class.java)

        initializeList()

        initializeProgressBar()

        initializeAdapter()

        return fragmentView
    }

    private fun getCurrentProgress() : Int {
        val max = notes.size.toDouble()
        var curr = 0
        notes.forEach { note ->
            if (note.status == Status.DONE) {
                curr++
            }
        }
        fragmentView.findViewById<TextView>(R.id.progressText).text = "Task(s): $curr/${max.roundToInt()}"
        return ((curr / max) * 100).toInt()
    }

    @SuppressLint("ResourceAsColor")
    fun initializeProgressBar() {
        val progressBar = fragmentView.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progressDrawable.setColorFilter(
            R.color.teal_200, android.graphics.PorterDuff.Mode.SRC_IN)
        progressBar.progress = getCurrentProgress()
    }

    private fun initializeList() {
        runBlocking {
            when (arguments?.getString(ToDoConstants.tabName)) {
                getString(R.string.myTasks) -> {
                    notes = noteViewModel.getNotes(username)
                }
                getString(R.string.subAccountTasks) -> {
                    notes = noteViewModel.getSubAccountsNotes(username)
                }
                getString(R.string.assignedTasks) ->
                    notes = noteViewModel.getAssignedNotes(username)
                else -> println("Invalid Tab")
            }

            println(arguments?.getString(ToDoConstants.tabName) + ", NOTES: " + notes.toString())
        }
    }

    private fun initializeAdapter() {

        //val activity = (activity as NotesActivity)
        notesAdapter = if (notes.isEmpty()) {
            NotesAdapter(mutableListOf(), noteViewModel, activity as NotesActivity, arguments?.getString(ToDoConstants.tabName), username)
        }
        else {
            NotesAdapter(notes as MutableList<Note>, noteViewModel, activity as NotesActivity, arguments?.getString(ToDoConstants.tabName), username)
        }

        val recyclerView: RecyclerView = fragmentView.findViewById(R.id.notes_recycler_view)!!
        val recycle: RecyclerView.LayoutManager =
            LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = recycle

        recyclerView.adapter = notesAdapter
    }

    fun updateList(notes: List<Note>) {
        this.notes = notes
        notesAdapter.update(notes)
        println("NOTES AT UPDATE LIST IN FRAGMENT: $notes")
    }

}