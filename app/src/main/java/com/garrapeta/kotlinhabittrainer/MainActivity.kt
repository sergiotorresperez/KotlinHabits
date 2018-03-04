package com.garrapeta.kotlinhabittrainer

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_add_habit -> {
                onNewHabitCreated()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onNewHabitCreated() {
        openActivity(CreateHabitActitivy::class.java)
    }

    private fun openActivity(activityClass : Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

}
