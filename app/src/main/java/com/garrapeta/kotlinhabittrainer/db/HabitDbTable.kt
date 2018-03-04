package com.garrapeta.kotlinhabittrainer.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
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

    private fun toByteArray(bitmap : Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
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
