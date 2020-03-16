package com.michaelcorral.ortimer.screens.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.michaelcorral.ortimer.R
import com.michaelcorral.ortimer.data.local.TimeEntry

class MainScreenAdapter(val onTimeEntryClicked: (TimeEntry) -> Unit) : RecyclerView.Adapter<MainScreenAdapter.ViewHolder>() {

    private val timeEntries = mutableListOf<TimeEntry>()

    fun addTimeEntries(timeEntries: List<TimeEntry>) {
        this.timeEntries.addAll(timeEntries)
        notifyDataSetChanged()
    }

    fun addTimeEntry(timeEntry: TimeEntry) {
        timeEntries.add(timeEntry)
        notifyDataSetChanged()
    }

    fun replaceTimeEntries(timeEntries: List<TimeEntry>) {
        clearTimeEntries()
        this.timeEntries.addAll(timeEntries)
        notifyDataSetChanged()
    }

    fun clearTimeEntries() {
        timeEntries.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mainscreen_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = timeEntries.size

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
       holder.bind(timeEntries[index])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val description: TextView = itemView.findViewById(R.id.mainScreenItemTextViewDescription)
        private val time: TextView = itemView.findViewById(R.id.mainScreenItemTextViewTime)
        private val container: ConstraintLayout = itemView.findViewById(R.id.mainScreenItemConstraintLayoutContainer)

        fun bind(timeEntry: TimeEntry) {
            description.text = timeEntry.description
            time.text = timeEntry.time

            container.setOnClickListener { onTimeEntryClicked(timeEntry) }
        }
    }
}