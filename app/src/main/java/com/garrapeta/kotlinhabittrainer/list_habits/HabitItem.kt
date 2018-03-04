package com.garrapeta.kotlinhabittrainer.list_habits

import com.garrapeta.kotlinhabittrainer.R

internal data class HabitItem(val seed: Int) {
    val title = "Title $seed"
    val description = "Description $seed"
    val imageResId = getImageFor(seed)

    private fun getImageFor(position: Int): Int {
        return when {
            (position % 2 == 0) -> R.drawable.walk
            else -> R.drawable.water
        }
    }
}