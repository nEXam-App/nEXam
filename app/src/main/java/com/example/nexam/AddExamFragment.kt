package com.example.nexam

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
import com.example.nexam.data.Exam
import com.example.nexam.databinding.FragmentAddExamBinding
import java.util.*

/**
 * Fragment to add or update an exam in the nEXam database.
 */
class AddExamFragment : Fragment() {

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: nEXamViewModel by activityViewModels {
        nEXamViewModelFactory(
            (activity?.application as nEXamApplication).database
                .ExamDao()
        )
    }
    private val navigationArgs: ExamDetailFragmentArgs by navArgs()

    lateinit var exam: Exam

    // Binding object instance corresponding to the fragment_add_exam.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding: FragmentAddExamBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddExamBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.examName.text.toString()
        )
    }

    /**
     * Binds views with the passed in [exam] information.
     */
    private fun bind(exam: Exam) {
        binding.apply {
            examName.setText(exam.nameOfSubject, TextView.BufferType.SPANNABLE)
            date.setText(exam.dateOfExam.toString(), TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateExam() }
        }
    }

    /**
     * Inserts the new exam into database and navigates up to list fragment.
     */
    private fun addNewExam() {
        val date = binding.date.text.toString()
        if (isEntryValid()) {
            viewModel.addNewExam(
                binding.examName.text.toString(),
                date,
            )
            val action = AddExamFragmentDirections.actionAddItemFragmentToItemListFragment()
            findNavController().navigate(action)
        }
    }

    /**
     * Updates an existing exam in the database and navigates up to list fragment.
     */
    private fun updateExam() {
        if (isEntryValid()) {
            val date = binding.date.text.toString()
            viewModel.updateExam(
                this.navigationArgs.examId,
                this.binding.examName.text.toString(),
                date
            )
            val action = AddExamFragmentDirections.actionAddItemFragmentToItemListFragment()
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

        val id = navigationArgs.examId
        if (id > 0) {
            viewModel.retrieveExam(id).observe(this.viewLifecycleOwner) { selectedExam ->
                exam = selectedExam
                bind(exam)
            }
        } else {
            binding.saveAction.setOnClickListener {
                addNewExam()
            }
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