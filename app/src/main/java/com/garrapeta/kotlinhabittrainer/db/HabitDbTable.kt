package com.garrapeta.kotlinhabittrainer.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.garrapeta.kotlinhabittrainer.domain.Habit
import java.io.ByteArrayOutputStream



class HabitDbTable(context : Context) {

    private val dbHelper = HabitTrainerSQLiteOpenHelper(context)

    fun store(habit : Habit) : Long? {
        val contentValues = ContentValues()
        with(contentValues) {
            put(HabitEntry.COL_TITLE, habit.title)
            put(HabitEntry.COL_DESCRIPTION, habit.description)
            put(HabitEntry.COL_IMAGE, toByteArray(habit.imageBitmap))
        }

        val db: SQLiteDatabase = dbHelper.writableDatabase
        return db.transaction({insert(HabitEntry.TABLE_NAME, null, contentValues)})
    }

    fun readAll() : List<Habit> {
        val habits = mutableListOf<Habit>()

        val db: SQLiteDatabase = dbHelper.writableDatabase
        val cursor = db.query(HabitEntry.TABLE_NAME, null, null, null, null, null, null)

        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            habits.add(createHabit(cursor))
            cursor.moveToNext()
        }
        cursor.close()

        return habits
    }

    private fun createHabit(cursor: Cursor): Habit {
        val title = cursor.getString(cursor.getColumnIndex(HabitEntry.COL_TITLE))
        val description = cursor.getString(cursor.getColumnIndex(HabitEntry.COL_DESCRIPTION))
        val imageBytes = cursor.getBlob(cursor.getColumnIndex(HabitEntry.COL_IMAGE))
        val imageBitmap = fromByteArray(imageBytes)

        return Habit(title, description, imageBitmap)
    }
}

private fun toByteArray(bitmap : Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
    return stream.toByteArray()
}

private fun fromByteArray(byteArray: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

private inline fun <T> SQLiteDatabase.transaction(transactionOperation: SQLiteDatabase.() -> T) : T {
    beginTransaction()

    return try {
        val resultValue : T = transactionOperation()
        setTransactionSuccessful()
        resultValue
    } finally {
        endTransaction()
        close()
    }
}
