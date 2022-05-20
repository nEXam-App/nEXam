package com.example.nexam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nexam.data.Exam
import com.example.nexam.databinding.FragmentExamDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * [ExamDetailFragment] displays the details of the selected exam.
 */
class ExamDetailFragment : Fragment() {
    private val navigationArgs: ExamDetailFragmentArgs by navArgs()
    lateinit var exam: Exam

    private val viewModel: nEXamViewModel by activityViewModels {
        nEXamViewModelFactory(
            (activity?.application as nEXamApplication).database.ExamDao(),
            (activity?.application as nEXamApplication).database.TopicDao()
        )
    }

    private var _binding: FragmentExamDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExamDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**
     * Binds views with the passed in exam data.
     */
    private fun bind(exam: Exam) {
        binding.apply {
            examName.text = exam.nameOfSubject
            date.text = exam.dateOfExam.toString()

            deleteExam.setOnClickListener { showConfirmationDialog() }
            editExam.setOnClickListener { editExam() }
        }
    }

    /**
     * Navigate to the Edit exam screen.
     */
    private fun editExam() {
        val action = ExamDetailFragmentDirections.actionExamDetailFragmentToAddExamFragment(
            getString(R.string.edit_fragment_title),
            exam.id
        )
        this.findNavController().navigate(action)
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the exam.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteExam()
            }
            .show()
    }

    /**
     * Deletes the current exam and navigates to the list fragment.
     */
    private fun deleteExam() {
        viewModel.deleteExam(exam)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.examId
        // Retrieve the exam details using the examId.
        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        viewModel.retrieveExam(id).observe(this.viewLifecycleOwner) { selectedExam ->
            exam = selectedExam
            bind(exam)
        }
        binding.addTopic.setOnClickListener {
            val action = ExamDetailFragmentDirections.actionExamDetailFragmentToAddTopicFragment(
                getString(R.string.add_new_topic)
            )
            this.findNavController().navigate(action)
        }
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}