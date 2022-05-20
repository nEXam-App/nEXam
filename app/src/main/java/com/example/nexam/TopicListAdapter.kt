package com.example.nexam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nexam.data.Exam
import com.example.nexam.data.Topic
import com.example.nexam.databinding.ExamListExamBinding
import com.example.nexam.databinding.TopicListTopicBinding

/**
 * [ListAdapter] implementation for the recyclerview.
 */

class TopicListAdapter(private val onTopicClicked: (Topic) -> Unit) :
    ListAdapter<Topic, TopicListAdapter.TopicViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        return TopicViewHolder(
            TopicListTopicBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onTopicClicked(current)
        }
        holder.bind(current)
    }

    class TopicViewHolder(private var binding: TopicListTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(topic: Topic) {
            binding.topicName.text = topic.nameOfSubject
            binding.remainingTime.text = topic.remainingTime as String

        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Topic>() {
            override fun areItemsTheSame(oldTopic: Topic, newTopic: Topic): Boolean {
                return oldTopic === newTopic
            }

            override fun areContentsTheSame(oldTopic: Topic, newTopic: Topic): Boolean {
                return oldTopic.nameOfTopic == newTopic.nameOfTopic
            }
        }
    }
}