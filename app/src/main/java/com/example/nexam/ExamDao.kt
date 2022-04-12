package com.example.nexam

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {
    @Query("SELECT * FROM exam_table ORDER BY subject ASC")
    fun getAlphabetizedExams(): Flow<List<Exam>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(exam: Exam)

    @Query("DELETE FROM exam_table")
    suspend fun deleteAll()

    /* @Query("SELECT * FROM exam WHERE subject = :subject")
     fun loadExamBySubject(subject: String): Exam

     @Query("SELECT subject FROM exam ORDER BY subject ASC")
     fun loadSubjectOrdered(): Flow<List<Subject>>

     @Query("SELECT * FROM exam ORDER BY subject ASC")
     fun loadExamsOrdered(): Flow<List<Exam>>

     @Query("DELETE FROM exam")
     suspend fun deleteAll()

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertExam(exam: Exam)

     @Update
     suspend fun updateExam(exam: Exam)

     @Delete
     suspend fun deleteExam(exam: Exam)*/
}