package com.rowaida.todo.presentation.adapter

import android.view.*
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.framework.ToDoApplication
import com.rowaida.todo.presentation.activity.NotesInterface
import com.rowaida.todo.presentation.viewModel.NoteViewModel
import kotlin.collections.HashMap


class DatesAdapter(private var notesHashMap: HashMap<String, MutableList<Note>>,
                   private var keys: Array<String>, val viewModel: NoteViewModel,
                   private val notesInterface: NotesInterface, private val tabName: String?,
                   private val username: String
) :
    RecyclerView.Adapter<DatesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.date)
        val notesView: RecyclerView = itemView.findViewById(R.id.notes_recycler_view)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item

        return ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.date_view, viewGroup, false)
        )

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.date.text = keys[position]
        val notesAdapter =
            notesHashMap[keys[position]]?.let {
                NotesAdapter(
                    it, viewModel, notesInterface,
                    tabName, username)
            }
        //FIXME move this to another method
        val recyclerView: RecyclerView = viewHolder.notesView
        val recycle: RecyclerView.LayoutManager =
            LinearLayoutManager(ToDoApplication.instance, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = recycle

        recyclerView.adapter = notesAdapter
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = notesHashMap.size

    fun update(updatedMap: HashMap<String, MutableList<Note>>) {
        notesHashMap = updatedMap
        notifyDataSetChanged()
    }

}


