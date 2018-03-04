package com.garrapeta.kotlinhabittrainer.db

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

inline fun <T> SQLiteDatabase.transaction(transactionOperation: SQLiteDatabase.() -> T) : T {
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

fun SQLiteDatabase.doQuery(distinct : Boolean = false,
                           table: String? = null,
                           columns : Array<String>? = null,
                           selection: String? = null,
                           selectionArgs: Array<String>? = null,
                           groupBy: String? = null,
                           having: String? = null,
                           orderBy: String? = null,
                           limit: String? = null): Cursor {

    return query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit)
}