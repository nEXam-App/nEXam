package com.example.nexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.nexam.R
import android.widget.ArrayAdapter
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import java.util.ArrayList

class Test : AppCompatActivity() {
    var people: MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //LISTVIEW
        val lv = findViewById<View>(R.id.lv) as ListView

        //FILL LIST
        fillPeople()

        //ADAPTER
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, people)
        lv.adapter = adapter
        lv.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(
                this@Test,
                people[i],
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun fillPeople() {
        people.clear()
        var p = Person()
        p.setName("Mike")
        people.add(p.getName())
        p = Person()
        p.setName("John")
        people.add(p.getName())
        p = Person()
        p.setName("Lucy")
        people.add(p.getName())
        p = Person()
        p.setName("Rebecca")
        people.add(p.getName())
        p = Person()
        p.setName("Kris")
        people.add(p.getName())
        p = Person()
        p.setName("Kurt")
        people.add(p.getName())
        p = Person()
        p.setName("Vin")
        people.add(p.getName())
    }
}