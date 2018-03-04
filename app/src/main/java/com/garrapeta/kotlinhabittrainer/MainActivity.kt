package com.garrapeta.kotlinhabittrainer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
    }

    private fun bindViews() {
        val items = (1..300)
                .map { HabitItem(it) }
                .toList()

        recyclerview.adapter = HabitAdapter(items)
        recyclerview.layoutManager = LinearLayoutManager(this)
    }


}
