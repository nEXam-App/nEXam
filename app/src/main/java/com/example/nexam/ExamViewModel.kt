package com.example.nexam

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ExamViewModel(private val repository: ExamRepository) : ViewModel() {
    val allExams: LiveData<List<Exam>> = repository.allExams.asLiveData()

    fun insert(exam: Exam) = viewModelScope.launch {
        repository.insertExam(exam)
    }
}

class ExamViewModelFactory(private val repository: ExamRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExamViewModel::class.java)) {
            Suppress("UNCHECKED_CAST")
            return ExamViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}