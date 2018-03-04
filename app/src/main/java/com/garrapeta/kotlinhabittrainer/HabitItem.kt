package com.garrapeta.kotlinhabittrainer

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