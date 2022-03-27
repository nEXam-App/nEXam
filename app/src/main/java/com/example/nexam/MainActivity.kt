package com.example.nexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.Button


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        loadView()

    }

    private fun loadView() {
        registerButton(R.id.createExamButton, R.layout.create_exam)
        registerButton(R.id.startTimer, R.layout.exam_view)
        registerButton(R.id.back, R.layout.dashboard)
        registerButton(R.id.save, R.layout.exam_success)
        registerButton(R.id.toDashboard, R.layout.dashboard)
        registerButton(R.id.editExam, R.layout.create_exam)
        registerButton(R.id.showExam, R.layout.exam_view)
        fillList(R.id.exam_list, R.array.test_exams)
        fillList(R.id.content_list, R.array.test_content)
    }

    private fun registerButton(button: Int, view: Int) {
        val button = findViewById<Button>(button)
        if (button == null) return
        button.setOnClickListener(View.OnClickListener {
            setContentView(view)
            loadView()
        })
    }

    private fun fillList(list: Int, array: Int) {
        val examListView: ListView? = findViewById(list)
        if (examListView == null) return
        examListView.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                resources.getStringArray(array)
            )

        examListView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                setContentView(R.layout.exam_view)
                loadView()
            }
    }
}