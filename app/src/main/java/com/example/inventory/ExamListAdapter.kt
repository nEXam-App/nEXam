
package com.example.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.data.Exam
import com.example.inventory.databinding.ExamListExamBinding
import java.util.*

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
        val current = getExam(position)
        holder.examView.setOnClickListener {
            onExamClicked(current)
        }
        holder.bind(current)
    }

    class ExamViewHolder(private var binding: ExamListExamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exam: Exam) {
            val dateBinded = Date(binding.date.text.toString())
            binding.examName.text = exam.nameOfSubject
            binding.date.text = dateBinded
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ExamCallback<Exam>() {
            override fun areExamsTheSame(oldExam: Exam, newExam: Exam): Boolean {
                return oldExam === newExam
            }

            override fun areContentsTheSame(oldExam: Exam, newExam: Exam): Boolean {
                return oldExam.nameOfSubject == newExam.nameOfSubject
            }
        }
    }
}
