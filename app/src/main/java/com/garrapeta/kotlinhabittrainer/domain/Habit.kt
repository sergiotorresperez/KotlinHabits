package com.garrapeta.kotlinhabittrainer.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.garrapeta.kotlinhabittrainer.R

data class Habit(val title : String, val description : String, val imageBitmap : Bitmap) {

    companion object {
        fun createDefaultHabits(context: Context) : List<Habit> {
            return listOf(
                    Habit("Drink water", "Drink water is good", getBitmapFor(context, R.drawable.water)),
                    Habit("Walk", "Walking everyday is great", getBitmapFor(context, R.drawable.walk))
            )
        }

        private fun getBitmapFor(context: Context, resId: Int): Bitmap {
            return BitmapFactory.decodeResource(context.resources, resId)
        }

    }

}