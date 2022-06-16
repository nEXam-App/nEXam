package com.example.nexam

import android.app.DatePickerDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nexam.data.Topic
import com.example.nexam.databinding.FragmentAddTopicBinding
import com.example.nexam.databinding.FragmentEditTopicBinding
import com.google.android.material.textfield.TextInputEditText
import java.util.*

/**
 * Fragment to add or update an exam in the nEXam database.
 */
class EditTopicFragment : Fragment() {


    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: nEXamViewModel by activityViewModels {
        nEXamViewModelFactory(
            (activity?.application as nEXamApplication).database
                .ExamDao(),
            (activity?.application as nEXamApplication).database
                .TopicDao()
        )
    }
    private val navigationArgs: EditTopicFragmentArgs by navArgs()
    lateinit var topic: Topic

    // Binding object instance corresponding to the fragment_add_exam.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding: FragmentEditTopicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditTopicBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    private fun isTopicEntryValid(): Boolean {
        return viewModel.isTopicEntryValid(
            binding.topicName.text.toString()
        )
    }

    /**
     * Binds views with the passed in [exam] information.
     */
    private fun bind(topic: Topic) {
        val exam = viewModel.retrieveExam(navigationArgs.examId).value
        binding.apply {
            topicName.setText(topic.nameOfTopic, TextView.BufferType.SPANNABLE)
            examName.setText(exam?.nameOfSubject)
            difficulty.setText(topic.difficulty, TextView.BufferType.SPANNABLE)
            remainingTime.setText(topic.remainingTime, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateTopic() }
        }
    }


    /**
     * Updates an existing exam in the database and navigates up to list fragment.
     */
    private fun updateTopic() {
        if (isTopicEntryValid()) {
            viewModel.updateTopic(
                this.navigationArgs.topicId,
                this.binding.topicName.text.toString(),
                navigationArgs.examId,
                this.binding.remainingTime as Int,
                this.binding.difficulty as Int,
                this.binding.checkboxProcess as Boolean
            )
            val action = AddTopicFragmentDirections.actionAddTopicFragmentToTopicListFragment(navigationArgs.examId)
            findNavController().navigate(action)
        }
    }

    /**
     * Called when the view is created.
     * The examId Navigation argument determines the edit exam  or add new exam.
     * If the examId is positive, this method retrieves the information from the database and
     * allows the user to update it.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.topicId

        viewModel.retrieveTopic(id).observe(this.viewLifecycleOwner) { selectedTopic ->
            topic = selectedTopic
            bind(topic)
        }

    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }




}
