package com.example.nexam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nexam.databinding.DashboardFragmentBinding

/**
 * Main fragment displaying details for all exams in the database.
 */
class DashboardFragment : Fragment() {
    private val viewModel: NexamViewModel by activityViewModels {
        NexamViewModelFactory(
            (activity?.application as NexamApplication).database.ExamDao()
        )
    }

    private var _binding: DashboardFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DashboardFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ExamListAdapter {
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToExamDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter
        // Attach an observer on the allExams list to update the UI automatically when the data
        // changes.
        viewModel.allExams.observe(this.viewLifecycleOwner) { exams ->
            exams.let {
                adapter.submitList(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToAddExamFragment(
                getString(R.string.add_fragment_title)
            )
            this.findNavController().navigate(action)
        }
    }
}