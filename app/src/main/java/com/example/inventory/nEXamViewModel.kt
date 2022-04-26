package com.example.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Exam
import com.example.inventory.data.ExamDao
import kotlinx.coroutines.launch
import java.util.*

/**
 * View Model to keep a reference to the Inventory repository and an up-to-date list of all items.
 *
 */
class nEXamViewModel(private val examDao: ExamDao) : ViewModel() {

    // Cache all items form the database using LiveData.
    val allExams: LiveData<List<Exam>> = examDao.getExam().asLiveData()


    /**
     * Updates an existing Item in the database.
     */
    fun updateExam(
        examId: Int,
        nameOfSubject: String,
        dateOfExam: Date
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
     * Inserts the new Item into database.
     */
    fun addNewExam(nameOfSubject: String, dateOfExam: Date) {
        val newExam = getNewExamEntry(nameOfSubject, dateOfExam)
        insertItem(newExam)
    }

    /**
     * Launching a new coroutine to insert an exam in a non-blocking way
     */
    private fun insertItem(exam: Exam) {
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
     * This will be used to add a new entry to the Inventory database.
     */
    private fun getNewExamEntry(examName: String, dateOfExam: Date): Exam {
        return Exam(
            nameOfSubject = examName,
            dateOfExam = dateOfExam
        )
    }

    /**
     * Called to update an existing entry in the Inventory database.
     * Returns an instance of the [Exam] entity class with the exam info updated by the user.
     */
    private fun getUpdatedExamEntry(
        examId: Int,
        examName: String,
        dateOfExam: Date
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
class nEXamViewModelFactory(private val examDao: ExamDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(nEXamViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return nEXamViewModel(examDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
