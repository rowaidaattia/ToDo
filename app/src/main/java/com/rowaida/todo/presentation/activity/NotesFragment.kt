package com.rowaida.todo.presentation.activity

import android.annotation.SuppressLint
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
import com.rowaida.todo.presentation.adapter.DatesAdapter
import com.rowaida.todo.presentation.viewModel.NoteViewModel
import com.rowaida.todo.utils.ToDoConstants
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class NotesFragment : Fragment() {

    lateinit var username : String
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var datesAdapter : DatesAdapter
    private lateinit var notes : List<Note>
    private lateinit var notesHashMap: HashMap<String, MutableList<Note>>
    private lateinit var fragmentView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        fragmentView = inflater.inflate(
            R.layout.layout_notes, container, false
        )

        username = arguments?.getString(ToDoConstants.username).toString()

        noteViewModel = ViewModelProvider(this, ToDoViewModelFactory)
            .get(NoteViewModel::class.java)

        initializeList()

        initializeHashMap()

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
        fragmentView.findViewById<TextView>(R.id.progressText).text =
            getString(R.string.tasks) + "$curr/${max.roundToInt()}"
        return ((curr / max) * 100).toInt()
    }

    @SuppressLint("ResourceAsColor")
    fun initializeProgressBar() {
        val progressBar = fragmentView.findViewById<ProgressBar>(R.id.progressBar)
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

        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun initializeHashMap() {
        notesHashMap = HashMap()
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        notes.forEach { note ->
            val key = sdf.format(note.date)
            if (notesHashMap.containsKey(key)) {
                notesHashMap[key]?.add(note)
            }
            else {
                notesHashMap[key] = mutableListOf(note)
            }
        }
    }

    private fun initializeAdapter() {

        //FIXME why do you init the adapter when the list is empty ? in order to update it right away once a user adds a note
        datesAdapter = if (notes.isEmpty()) {
            DatesAdapter(HashMap(), arrayOf(), noteViewModel, activity as NotesActivity, arguments?.getString(ToDoConstants.tabName), username)
        }
        else {
            DatesAdapter(notesHashMap, notesHashMap.keys.toTypedArray(), noteViewModel, activity as NotesActivity, arguments?.getString(ToDoConstants.tabName), username)
        }

        val recyclerView: RecyclerView = fragmentView.findViewById(R.id.dates_recycler_view)!!
        val recycle: RecyclerView.LayoutManager =
            LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = recycle

        recyclerView.adapter = datesAdapter
    }

    fun updateList(notes: List<Note>) {
        this.notes = notes
        initializeHashMap()
        datesAdapter.update(notesHashMap)
    }

}