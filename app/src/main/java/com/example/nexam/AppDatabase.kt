package com.example.nexam

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Exam::class], version = 1, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun examDao(): ExamDao

    private class ExamDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var examDao = database.examDao()

                    // Delete all content here.
                    examDao.deleteAll()

                    // Add sample words.
                    var exam = Exam("Mathematik")
                    examDao.insert(exam)
                    exam = Exam("Anwendungen der Robotik")
                    examDao.insert(exam)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "exam_database"
                ).addCallback(ExamDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
