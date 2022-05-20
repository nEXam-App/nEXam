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
        parentColumns = ["subject"],
        childColumns = ["subject"],
        onDelete = ForeignKey.CASCADE)])
data class Topic(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val nameOfTopic: String,
    @ColumnInfo(name = "subject")
    val nameOfSubject: String,
    @ColumnInfo(name = "difficulty")
    val difficulty: Int,
    @ColumnInfo(name = "time")
    val remainingTime: Int,
    @ColumnInfo(name = "status")
    val process: Boolean
)