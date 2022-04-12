package com.example.nexam

import androidx.room.*
import java.sql.Time

@Entity(tableName = "topic_table")
class Topic(
    @PrimaryKey @ColumnInfo(name = "topic") val topic:String,
    @ColumnInfo(name = "subject") val subject:String,
    @ColumnInfo(name ="time") val time:Time,
    @ColumnInfo(name = "status") val status:Boolean
)