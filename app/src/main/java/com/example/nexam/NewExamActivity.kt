package com.example.nexam

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewExamActivity : AppCompatActivity() {

    private lateinit var createExamView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_exam)
        createExamView = findViewById(R.id.input_subject)

        val button = findViewById<Button>(R.id.save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(createExamView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val exam = createExamView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, exam)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}