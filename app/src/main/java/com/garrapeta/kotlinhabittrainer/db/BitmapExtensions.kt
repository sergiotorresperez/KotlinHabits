package com.garrapeta.kotlinhabittrainer.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

fun Bitmap.toByteArray() : ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 0, stream)
    return stream.toByteArray()
}

fun ByteArray.toBitmap() = BitmapFactory.decodeByteArray(this, 0, this.size)
