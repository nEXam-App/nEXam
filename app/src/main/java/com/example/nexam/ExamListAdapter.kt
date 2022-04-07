package com.example.nexam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ExamListAdapter : ListAdapter<Exam, ExamListAdapter.ExamViewHolder>(ExamsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        return ExamViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.exam)
    }

    class ExamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val examItemView: TextView = itemView.findViewById(R.id.tvExam)

        fun bind(text: String?) {
            examItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): ExamViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.dashboard_recyclerview_item, parent, false)
                return ExamViewHolder(view)
            }
        }
    }

    class ExamsComparator : DiffUtil.ItemCallback<Exam>() {
        override fun areItemsTheSame(oldItem: Exam, newItem: Exam): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Exam, newItem: Exam): Boolean {
            return oldItem.exam == newItem.exam
        }
    }
}
