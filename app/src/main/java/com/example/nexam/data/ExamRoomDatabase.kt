package com.example.nexam.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton INSTANCE object.
 */
@Database(entities = [Exam::class, Topic::class], version = 1, exportSchema = false)
abstract class ExamRoomDatabase : RoomDatabase() {

    abstract fun ExamDao(): ExamDao
    abstract fun TopicDao(): TopicDao

    companion object {
        @Volatile
        private var INSTANCE: ExamRoomDatabase? = null

        fun getDatabase(context: Context): ExamRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExamRoomDatabase::class.java,
                    "exam_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this code.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}