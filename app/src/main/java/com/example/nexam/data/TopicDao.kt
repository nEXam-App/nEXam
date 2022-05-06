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
interface TopicDao {

    @Query("SELECT * from topic ORDER BY name ASC")
    fun getTopic(): Flow<List<Topic>>

    @Query("SELECT * from topic WHERE id = :id")
    fun getTopic(id: Int): Flow<Topic>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Exam into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(topic: Topic)

    @Update
    suspend fun update(topic: Topic)

    @Delete
    suspend fun delete(topic: Topic)
}
