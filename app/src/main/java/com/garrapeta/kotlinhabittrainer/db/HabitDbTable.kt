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

        val db: SQLiteDatabase = dbHelper.writableDatabase

        db.use {
            with(ContentValues()) {

                put(HabitEntry.COL_TITLE, habit.title)
                put(HabitEntry.COL_DESCRIPTION, habit.description)
                put(HabitEntry.COL_IMAGE, toByteArray(habit.imageBitmap))

                return try {
                    db.beginTransaction()
                    val returnValue = db.insert(HabitEntry.TABLE_NAME, null, this)
                    db.setTransactionSuccessful()
                    returnValue
                } catch (e : Exception) {
                    null
                } finally {
                    db.endTransaction()
                }
            }
        }

    }

    private fun toByteArray(bitmap : Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
}
