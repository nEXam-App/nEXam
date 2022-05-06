package com.example.nexam.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "exam",
    indices = [Index(value = ["subject"], unique = true)])
data class Exam(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "subject")
    val nameOfSubject: String,
    @ColumnInfo(name = "date")
    val dateOfExam: String,
)