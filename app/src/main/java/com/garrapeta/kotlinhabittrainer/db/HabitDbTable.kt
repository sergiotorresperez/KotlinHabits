package com.garrapeta.kotlinhabittrainer.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.garrapeta.kotlinhabittrainer.db.HabitEntry.COL_DESCRIPTION
import com.garrapeta.kotlinhabittrainer.db.HabitEntry.COL_IMAGE
import com.garrapeta.kotlinhabittrainer.db.HabitEntry.COL_TITLE
import com.garrapeta.kotlinhabittrainer.db.HabitEntry.TABLE_NAME
import com.garrapeta.kotlinhabittrainer.domain.Habit

class HabitDbTable(context : Context) {

    private val dbHelper = HabitTrainerSQLiteOpenHelper(context)

    fun store(habit : Habit) : Long? {
        val contentValues = ContentValues()
        with(contentValues) {
            put(COL_TITLE, habit.title)
            put(COL_DESCRIPTION, habit.description)
            put(COL_IMAGE, habit.imageBitmap.toByteArray())
        }

        val db: SQLiteDatabase = dbHelper.writableDatabase
        return db.transaction({insert(TABLE_NAME, null, contentValues)})
    }

    fun readAll() : List<Habit> {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val projection = arrayOf(COL_TITLE, COL_DESCRIPTION, COL_IMAGE)
        val orderBy = "${HabitEntry.COL_ID} ASC"
        val cursor = db.doQuery(table = TABLE_NAME, columns = projection, orderBy = orderBy)

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
        val title = cursor.getString(COL_TITLE)
        val description = cursor.getString(COL_DESCRIPTION)
        val imageBitmap = cursor.getBitmap(COL_IMAGE)

        return Habit(title, description, imageBitmap)
    }
}

