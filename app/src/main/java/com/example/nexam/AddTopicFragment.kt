package com.example.nexam

import android.app.DatePickerDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
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
import com.google.android.material.textfield.TextInputEditText
import java.util.*

/**
 * Fragment to add or update an exam in the nEXam database.
 */
class AddTopicFragment : Fragment() {

    var textview_date: TextInputEditText? = null

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
    private val navigationArgs: AddTopicFragmentArgs by navArgs()

    // Binding object instance corresponding to the fragment_add_exam.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding: FragmentAddTopicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTopicBinding.inflate(inflater, container, false)
        binding.examName.setText(navigationArgs.examName)
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
     * Inserts the new exam into database and navigates up to list fragment.
     */
    private fun addNewTopic() {
        val difficulty = binding.difficulty.text.toString().toInt()
        if (isTopicEntryValid()) {
            binding.apply {
                examName.setText(navigationArgs.examName)
            }
            viewModel.addNewTopic(
                navigationArgs.examId,
                binding.topicName.text.toString(),
                difficulty
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

        binding.saveAction.setOnClickListener {
            addNewTopic()
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
