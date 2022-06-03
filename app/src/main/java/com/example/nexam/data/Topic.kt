package com.example.nexam.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*
/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "topic",
    foreignKeys = [ForeignKey(entity = Exam::class,
        parentColumns = ["examId"],
        childColumns = ["subjectId"],
        onDelete = ForeignKey.CASCADE)])
data class Topic(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val nameOfTopic: String,
    @ColumnInfo(name = "subjectId")
    val idOfSubject: Int,
    @ColumnInfo(name = "difficulty")
    val difficulty: Int,
    @ColumnInfo(name = "time")
    val remainingTime: Int,
    @ColumnInfo(name = "status")
    val process: Boolean
)