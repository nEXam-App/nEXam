package com.example.nexam

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.text.TextUtils
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    var counter = 0

   /* private val examViewModel: ExamViewModel by viewModels {
        ExamViewModelFactory((application as ExamsApplication).repository)
    }*/

    /*private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                NewExamActivity.EXTRA_REPLY?.let { reply ->
                    val exam = Exam(reply)
                    examViewModel.insert()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            }
        }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

       /* val recyclerView = findViewById<RecyclerView>(R.id.exam_list)
        val adapter = ExamListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)*/

        /*examViewModel.allExams.observe(this) { exams ->
            // Update the cached copy of the exams in the adapter.
            exams.let { adapter.submitList(it) }
        }*/

        //loadView()

        /*val saveBtn = findViewById<Button>(R.id.save)
        saveBtn.setOnClickListener {
            startForResult.launch(Intent(this, NewExamActivity::class.java))
        }*/
    }

    private fun loadView() {
        registerButton(R.id.createExamButton, R.layout.create_exam)
        registerButton(R.id.back, R.layout.dashboard)
        registerButton(R.id.save, R.layout.exam_success)
        registerButton(R.id.toDashboard, R.layout.dashboard)
        registerButton(R.id.editExam, R.layout.create_exam)
        registerButton(R.id.showExam, R.layout.exam_view)
        //fillList(R.id.exam_list, R.array.test_exams)
        fillList(R.id.content_list, R.array.test_content)
        //addTimer()
    }

    private fun registerButton(button: Int, view: Int) {
        val button = findViewById<Button>(button) ?: return
        button.setOnClickListener {
            setContentView(view)
            loadView()
        }
    }

    private fun fillList(list: Int, array: Int) {
        val examListView: ListView = findViewById(list) ?: return
        examListView.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                resources.getStringArray(array)
            )

        examListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, _ ->
                setContentView(R.layout.exam_view)
                loadView()
            }
    }

    /*private fun addTimer() {
        val button = findViewById<Button>(R.id.startTimer) ?: return
        button.setOnClickListener {
            setContentView(R.layout.dashboard)
            loadView()
            startTimeCounter()
        }
    }*/

    private fun startTimeCounter() {
        val countTime: EditText = findViewById(R.id.countTime)
        //TODO take input as formatted text
        //TODO exception handling
        //TODO stop timer instead of start timer

        var enteredTime: Long = if (!TextUtils.isEmpty(countTime.text)) {
            countTime.text.toString().toLong()
        } else {
            1200000
        }

        object : CountDownTimer(enteredTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60

                countTime.setText(
                    getString(
                        R.string.countTimeText,
                        f.format(hour),
                        f.format(min),
                        f.format(sec)
                    )
                )
                counter++
                countTime.isEnabled = false
            }

            override fun onFinish() {
                countTime.setText(getString(R.string.countTimeFinished))
                countTime.isEnabled = true
            }
        }.start()
    }
}