package com.example.nexam

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    //var remainingMillis: Long = 0
    var remainingTime: Long = 0
    lateinit var timer: CountDownTimer
    var timerRunning = false

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
            date.text = exam.dateOfExam
            difficulty.text = exam.difficulty.toString()
            additionalNotes.text = exam.additionalNotes
            finishedLabel.text = "Finished"
            switchFinished.isChecked = exam.finished

            if (exam.finished) {
                finishedLabel.visibility = View.VISIBLE
            } else {
                finishedLabel.visibility = View.GONE
            }

            startTimer.setOnClickListener { startTimeCounter() }
            stopTimer.setOnClickListener { stopTimeCounter() }
            if(!timerRunning) {
                stopTimer.isEnabled = false
            }
            deleteExam.setOnClickListener { showDeleteConfirmationDialog() }
            editExam.setOnClickListener { editExam() }
            switchFinished.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) showFinishConfirmationDialog() else unfinishExam()
            }
        }
    }


    private fun startTimeCounter() {
        initTimer()
        timer.start()
        val message = "timer started"
        var toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast.show()
        timerRunning = true
        binding.startTimer.isEnabled = false
        binding.stopTimer.isEnabled = true
    }

    private fun initTimer(){
        timer = object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                setTimerText(remainingTime)
            }

            override fun onFinish() {
                binding.remainingTime.setText(getString(R.string.countTimeFinished))
            }
        }
    }

    private fun stopTimeCounter() {
        timer.cancel()
        setTimerText(remainingTime)
        timerRunning = false
        binding.startTimer.isEnabled = true
        binding.stopTimer.isEnabled = false
        exam.remainingTime = remainingTime
        viewModel.updateExam(exam)
    }

    private fun setTimerText(millisUntilFinished: Long) {
        val f: NumberFormat = DecimalFormat("00")
        val hour = millisUntilFinished / 3600000
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
    }


    /**
     * Navigate to the Edit exam screen.
     */
    private fun editExam() {
        if (timerRunning) {
            timer.cancel()
            timerRunning = false
        }
        exam.remainingTime = remainingTime
        viewModel.updateExam(exam)
        val action = ExamDetailFragmentDirections.actionExamDetailFragmentToAddExamFragment(
            getString(R.string.edit_fragment_title),
            exam.id
        )
        this.findNavController().navigate(action)
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the exam.
     */
    private fun showDeleteConfirmationDialog() {
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

    private fun showFinishConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.finish_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                binding.switchFinished.isChecked = false
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                finishExam()
            }
            .show()
    }

    private fun finishExam() {
        binding.finishedLabel.visibility = View.VISIBLE
        exam.finished = true
        initTimer()

        timer.cancel()
        timerRunning = false
        binding.startTimer.isEnabled = true
        binding.stopTimer.isEnabled = false
        exam.remainingTime = remainingTime


        viewModel.updateExam(exam)
    }

    private fun unfinishExam(){
        binding.finishedLabel.visibility = View.GONE
        exam.finished = false
        viewModel.updateExam(exam)
    }

    /**
     * Deletes the current exam and navigates to the list fragment.
     */
    private fun deleteExam() {
        if (timerRunning) {
            timer.cancel()
            timerRunning = false
        }
        viewModel.deleteExam(exam)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.examId
        if(timerRunning == true){
            binding.startTimer.isEnabled = false
            binding.stopTimer.isEnabled = true
        }

        // Retrieve the exam details using the examId.
        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        viewModel.retrieveExam(id).observe(this.viewLifecycleOwner) { selectedExam ->
            exam = selectedExam
            remainingTime = exam.remainingTime
            setTimerText(remainingTime)
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