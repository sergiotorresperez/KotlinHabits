package com.garrapeta.kotlinhabittrainer.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.garrapeta.kotlinhabittrainer.domain.Habit

class HabitDbTable(context : Context) {

    private val dbHelper = HabitTrainerSQLiteOpenHelper(context)

    fun store(habit : Habit) : Long? {
        val contentValues = ContentValues()
        with(contentValues) {
            put(HabitEntry.COL_TITLE, habit.title)
            put(HabitEntry.COL_DESCRIPTION, habit.description)
            put(HabitEntry.COL_IMAGE, habit.imageBitmap.toByteArray())
        }

        val db: SQLiteDatabase = dbHelper.writableDatabase
        return db.transaction({insert(HabitEntry.TABLE_NAME, null, contentValues)})
    }

    fun readAll() : List<Habit> {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val projection = arrayOf(HabitEntry.COL_TITLE, HabitEntry.COL_DESCRIPTION, HabitEntry.COL_IMAGE)
        val orderBy = "${HabitEntry.COL_ID} ASC"
        val cursor = db.doQuery(table = HabitEntry.TABLE_NAME, columns = projection, orderBy = orderBy)

        return parseHabits(cursor)
    }

    private fun parseHabits(cursor: Cursor): MutableList<Habit> {
        val habits = mutableListOf<Habit>()

        cursor.use {
            while (it.moveToNext()) {
                habits.add(parseHabit(it))
            }
        }

        return habits
    }

    private fun parseHabit(cursor: Cursor): Habit {
        val title = cursor.getString(HabitEntry.COL_TITLE)
        val description = cursor.getString(HabitEntry.COL_DESCRIPTION)
        val imageBitmap = cursor.getBitmap(HabitEntry.COL_IMAGE)

        return Habit(title, description, imageBitmap)
    }
}

