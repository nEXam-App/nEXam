package com.example.nexam

import android.app.Application
import com.example.nexam.data.ExamRoomDatabase


class nEXamApplication : Application() {
    // Using by lazy so the database is only created when needed
    // rather than when the application starts
    val database: ExamRoomDatabase by lazy { ExamRoomDatabase.getDatabase(this) }
}
