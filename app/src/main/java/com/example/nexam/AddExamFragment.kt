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
import com.example.nexam.data.Exam
import com.example.nexam.databinding.FragmentAddExamBinding
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import java.text.SimpleDateFormat

/**
 * Fragment to add or update an exam in the nEXam database.
 */
class AddExamFragment : Fragment() {

    var textview_date: TextInputEditText? = null
    var cal = Calendar.getInstance()

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: NexamViewModel by activityViewModels {
        NexamViewModelFactory(
            (activity?.application as NexamApplication).database
                .ExamDao()
        )
    }
    private val navigationArgs: ExamDetailFragmentArgs by navArgs()

    lateinit var exam: Exam
    var oldDifficulty: Int = 0

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
        addPickDate()

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
            difficulty.setText(exam.difficulty.toString(), TextView.BufferType.SPANNABLE)
            remainingTime.setText(exam.remainingTime.toString(), TextView.BufferType.SPANNABLE)
            additionalNotes.setText(exam.additionalNotes, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateExam() }
        }
    }

    /**
     * Inserts the new exam into database and navigates up to list fragment.
     */
    private fun addNewExam() {
        val date = binding.date.text.toString()
        var difficulty: Int = Integer.parseInt(binding.difficulty.text.toString())
        if(difficulty > 5){
            difficulty = 5
        }
        val additionalNotes: String = binding.additionalNotes.text.toString()
        val finished = false
        if (isEntryValid()) {
            viewModel.addNewExam(
                binding.examName.text.toString(),
                date,
                difficulty,
                additionalNotes,
                finished
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
            var remainingTime: Long
            val date = binding.date.text.toString()
            val difficulty: Int = Integer.parseInt(binding.difficulty.text.toString())
            val additionalNotes: String = binding.additionalNotes.text.toString()
            when(oldDifficulty.equals(difficulty)){
                true -> remainingTime = binding.remainingTime.text.toString().toLong()
                false -> remainingTime = (binding.difficulty.text.toString().toLong() * 3600000 * 10)
            }
            viewModel.updateExam(
                this.navigationArgs.examId,
                this.binding.examName.text.toString(),
                date,
                difficulty,
                remainingTime,
                additionalNotes
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
        binding.remainingTimeLabel.visibility = View.GONE
        val id = navigationArgs.examId
        if (id > 0) {
            viewModel.retrieveExam(id).observe(this.viewLifecycleOwner) { selectedExam ->
                exam = selectedExam
                bind(exam)
                oldDifficulty = exam.difficulty
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

    /**
     * Update Date in View
     */
    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.GERMANY)
        textview_date?.setText(sdf.format(cal.getTime()), TextView.BufferType.SPANNABLE)
    }

    /**
     * Adds dataSet and onClick Listener
     */
    private fun addPickDate(){
        // get the references from layout file
        textview_date = binding.date

        // create an OnDateSetListener
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        textview_date!!.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}