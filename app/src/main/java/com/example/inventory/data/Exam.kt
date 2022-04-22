package com.example.inventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat
import java.util.*

/**
 * Entity data class represents a single row in the database.
 */
@Entity
data class Exam(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val nameOfSubject: String,
    @ColumnInfo(name = "price")
    val dateOfExam: Date,
)
/**
 * Returns the passed in price in currency format.
 */
fun Exam.getFormattedPrice(): String =
    NumberFormat.getCurrencyInstance().format(dateOfExam)