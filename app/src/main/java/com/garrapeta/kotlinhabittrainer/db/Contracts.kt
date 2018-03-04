package com.garrapeta.kotlinhabittrainer.db

import android.provider.BaseColumns


val DATABASE_NAME= "habits.db"
val DATABASE_VERSION = 10


object HabitEntry : BaseColumns {
    val TABLE_NAME = "habit"

    val COL_ID = "_id"
    val COL_TITLE = "title"
    val COL_DESCRIPTION = "description"
    val COL_IMAGE = "image"
}