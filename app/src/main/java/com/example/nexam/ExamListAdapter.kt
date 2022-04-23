package com.example.nexam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nexam.data.Exam
import com.example.nexam.databinding.ExamListExamBinding

/**
 * [ListAdapter] implementation for the recyclerview.
 */

class ExamListAdapter(private val onExamClicked: (Exam) -> Unit) :
    ListAdapter<Exam, ExamListAdapter.ExamViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        return ExamViewHolder(
            ExamListExamBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onExamClicked(current)
        }
        holder.bind(current)
    }

    class ExamViewHolder(private var binding: ExamListExamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exam: Exam) {
            binding.examName.text = exam.nameOfSubject
            binding.date.text = exam.dateOfExam.toString()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Exam>() {
            override fun areItemsTheSame(oldExam: Exam, newExam: Exam): Boolean {
                return oldExam === newExam
            }

            override fun areContentsTheSame(oldExam: Exam, newExam: Exam): Boolean {
                return oldExam.nameOfSubject == newExam.nameOfSubject
            }
        }
    }
}