package com.example.nexam

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import android.os.Bundle
import android.os.CountDownTimer
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
    var counter = 0
    var runningTimer = false

    private val viewModel: NexamViewModel by activityViewModels {
        NexamViewModelFactory(
            (activity?.application as NexamApplication).database.ExamDao()
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
            difficulty.text = exam.difficulty.toString()
            remainingTime.text = exam.remainingTime.toString()

            timer.setOnClickListener{startTimeCounter()}
            deleteExam.setOnClickListener { showConfirmationDialog() }
            editExam.setOnClickListener { editExam() }
        }
    }


    private fun startTimeCounter() {
        //val countTime: InputTextField = findViewById(R.id.countTime)
        var timer = object : CountDownTimer(binding.remainingTime.text.toString().toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60

                binding.remainingTime.setText(
                    getString(
                        R.string.countTimeText,
                        f.format(hour),
                        f.format(min),
                        f.format(sec)
                    )
                )
                //counter++
                binding.remainingTime.isEnabled = false
            }

            override fun onFinish() {
                binding.remainingTime.setText(getString(R.string.countTimeFinished))
                binding.remainingTime.isEnabled = true
            }
        }
        if (runningTimer == false){
            timer.start()
            runningTimer = true
        }
        else{
            timer.cancel()
            runningTimer = false
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
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}