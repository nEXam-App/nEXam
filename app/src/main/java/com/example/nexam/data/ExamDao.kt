package com.example.nexam.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the nEXam database
 */
@Dao
interface ExamDao {

    @Query("SELECT * from exam ORDER BY subject ASC")
    fun getExam(): Flow<List<Exam>>

    @Query("SELECT * from exam WHERE id = :id")
    fun getExam(id: Int): Flow<Exam>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Exam into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(exam: Exam)

    @Update
    suspend fun update(exam: Exam)

    @Delete
    suspend fun delete(exam: Exam)
}
