package com.garrapeta.kotlinhabittrainer.db

import android.database.Cursor

fun Cursor.getString(tableName : String) : String = getString(getColumnIndex(tableName))

fun Cursor.getBlob(tableName : String) = getBlob(getColumnIndex(tableName))

fun Cursor.getBitmap(tableName : String) = getBlob(tableName).toBitmap()