package com.example.nexam

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.nexam.data.Exam
import com.example.nexam.data.ExamDao
import com.example.nexam.data.Topic
import com.example.nexam.data.TopicDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import java.util.*

/**
 * View Model to keep a reference to the nEXam repository and an up-to-date list of all exams.
 *
 */
class nEXamViewModel(private val examDao: ExamDao, private  val topicDao: TopicDao) : ViewModel() {

    // Cache all exams form the database using LiveData.
    val allExams: LiveData<List<Exam>> = examDao.getExam().asLiveData()
    val allTopics: LiveData<List<Topic>> = topicDao.getTopics().asLiveData()


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
            examId = examId,
            nameOfSubject = examName,
            dateOfExam = dateOfExam
        )
    }

    ///
    /**
     * Updates an existing topic in the database.
     */
    fun updateTopic(
        topicId: Int,
        nameOfTopic: String,
        examId: Int,
        remainingTime: Int,
        difficulty: Int,
        process: Boolean
    ) {
        val updatedTopic = getUpdatedTopicEntry(topicId, nameOfTopic, examId, remainingTime, difficulty, process)
        updateTopic(updatedTopic)
    }


    /**
     * Launching a new coroutine to update an exam in a non-blocking way
     */
    private fun updateTopic(topic: Topic) {
        viewModelScope.launch {
            topicDao.update(topic)
        }
    }

    /**
     * Inserts the new exam into database.
     */
    fun addNewTopic(examId: Int, nameOfTopic: String, difficulty: Int) {
        val newTopic = getNewTopicEntry(examId, nameOfTopic, difficulty)
        insertTopic(newTopic)
        Log.i("success","Topic was added")
    }

    /**
     * Launching a new coroutine to insert an exam in a non-blocking way
     */
    private fun insertTopic(topic: Topic) {
        viewModelScope.launch {
            Log.i("success", "topicid: "+topic.id.toString())
            Log.i("success", "topicexamid: "+topic.idOfSubject.toString())
            topicDao.insert(topic)
            Log.i("success","Topic was inseret")
            val ret: Topic? = topicDao.getTopic(topic.id).asLiveData().value
            val ex: Exam? = examDao.getExam(topic.idOfSubject).asLiveData().value
            if (ret != null) {
                Log.i("success", ret.nameOfTopic)
            }
            else{
                Log.i("success", "looser")
                if (ex != null) {
                    Log.i("success",ex.nameOfSubject)
                }
                else{
                    Log.i("success", allExams.toString())
                    Log.i("success", "looser again")
                }
            }
        }
    }

    /**
     * Launching a new coroutine to delete an exam in a non-blocking way
     */
    fun deleteTopic(topic: Topic) {
        viewModelScope.launch {
            topicDao.delete(topic)
        }
    }

    /**
     * Retrieve an exam from the repository.
     */
    fun retrieveTopic(id: Int): LiveData<Topic> {
        return topicDao.getTopic(id).asLiveData()
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    fun isTopicEntryValid(nameOfTopic: String): Boolean {
        if (nameOfTopic.isBlank()) {
            return false
        }
        return true
    }

    /**
     * Returns an instance of the [Exam] entity class with the exam info entered by the user.
     * This will be used to add a new entry to the nEXam database.
     */
    private fun getNewTopicEntry(examId: Int, nameOfTopic: String, difficulty: Int): Topic {
        return Topic(
            idOfSubject = examId,
            nameOfTopic = nameOfTopic,
            difficulty = difficulty,
            remainingTime = (difficulty * 3600000 * 4),
            process = false
        )
    }

    /**
     * Called to update an existing entry in the nEXam database.
     * Returns an instance of the [Exam] entity class with the exam info updated by the user.
     */
    private fun getUpdatedTopicEntry(Id: Int,  nameOfTopic: String, examId: Int, remainingTime: Int, difficulty: Int, process: Boolean): Topic {
        return Topic(
            id = Id,
            nameOfTopic = nameOfTopic,
            idOfSubject = examId,
            remainingTime = remainingTime,
            difficulty = difficulty,
            process = process
        )
    }
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class nEXamViewModelFactory(private val examDao: ExamDao, private val topicDao: TopicDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(nEXamViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return nEXamViewModel(examDao, topicDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}