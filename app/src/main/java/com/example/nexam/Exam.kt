package com.example.nexam

import androidx.room.*
import java.util.*

@Entity(tableName = "exam_table")
class Exam(
    @PrimaryKey @ColumnInfo(name = "subject") val subject: String,
    @ColumnInfo(name = "date") val date:String,
    @ColumnInfo(name = "progress") val progrss:Boolean
)

/*data class Exam(
    @PrimaryKey(autoGenerate = true) val examId: Int,
    @ColumnInfo(name = "subject") val subject: String,
    @ColumnInfo(name = "topic") val topic: String?,
    @ColumnInfo(name = "date") val date: String?,
)

data class Subject(
    @ColumnInfo(name = "subject") val subject: String?
)*/