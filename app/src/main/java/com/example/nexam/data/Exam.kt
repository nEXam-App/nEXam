package com.example.nexam.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Entity data class represents a single row in the database.
 */
@Entity
data class Exam(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "subject")
    val nameOfSubject: String,
    @ColumnInfo(name = "date")
    val dateOfExam: String,
    @ColumnInfo(name = "difficulty")
    val difficulty: Int,
    @ColumnInfo(name = "remainingTime")
    var remainingTime: Int,
    @ColumnInfo(name = "additionalNotes")
    var additionalNotes: String,
    @ColumnInfo(name = "finished")
    var finished: Boolean
)