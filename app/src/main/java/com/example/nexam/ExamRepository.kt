package com.example.nexam

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ExamRepository (private val examDao: ExamDao){
    val allExams:Flow<List<Exam>> =examDao.getAlphabetizedExams()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertExam(exam: Exam){
        examDao.insert(exam)
    }
}