package com.example.nexam

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.nexam.data.Exam
import com.example.nexam.data.ExamDao
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the nEXam repository and an up-to-date list of all exams.
 *
 */
class NexamViewModel(private val examDao: ExamDao) : ViewModel() {

    // Cache all exams form the database using LiveData.
    val allExams: LiveData<List<Exam>> = examDao.getExam().asLiveData()


    /**
     * Updates an existing exam in the database.
     */
    fun updateExam(
        examId: Int,
        nameOfSubject: String,
        dateOfExam: String
    ) {
        val updatedExam = getUpdatedExamEntry(examId, nameOfSubject, dateOfExam)
        updateExam(updatedExam)
    }


    /**
     * Launching a new coroutine to update an exam in a non-blocking way
     */
    private fun updateExam(exam: Exam) {
        viewModelScope.launch {
            examDao.update(exam)
        }
    }

    /**
     * Inserts the new exam into database.
     */
    fun addNewExam(nameOfSubject: String, dateOfExam: String) {
        val newExam = getNewExamEntry(nameOfSubject, dateOfExam)
        insertExam(newExam)
    }

    /**
     * Launching a new coroutine to insert an exam in a non-blocking way
     */
    private fun insertExam(exam: Exam) {
        viewModelScope.launch {
            examDao.insert(exam)
        }
    }

    /**
     * Launching a new coroutine to delete an exam in a non-blocking way
     */
    fun deleteExam(exam: Exam) {
        viewModelScope.launch {
            examDao.delete(exam)
        }
    }

    /**
     * Retrieve an exam from the repository.
     */
    fun retrieveExam(id: Int): LiveData<Exam> {
        return examDao.getExam(id).asLiveData()
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValid(nameOfSubject: String): Boolean {
        if (nameOfSubject.isBlank()) {
            return false
        }
        return true
    }

    /**
     * Returns an instance of the [Exam] entity class with the exam info entered by the user.
     * This will be used to add a new entry to the nEXam database.
     */
    private fun getNewExamEntry(examName: String, dateOfExam: String): Exam {
        return Exam(
            nameOfSubject = examName,
            dateOfExam = dateOfExam
        )
    }

    /**
     * Called to update an existing entry in the nEXam database.
     * Returns an instance of the [Exam] entity class with the exam info updated by the user.
     */
    private fun getUpdatedExamEntry(
        examId: Int,
        examName: String,
        dateOfExam: String
    ): Exam {
        return Exam(
            id = examId,
            nameOfSubject = examName,
            dateOfExam = dateOfExam
        )
    }
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class NexamViewModelFactory(private val examDao: ExamDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NexamViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NexamViewModel(examDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}