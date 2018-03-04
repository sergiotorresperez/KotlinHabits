package com.garrapeta.kotlinhabittrainer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

internal class HabitAdapter(private val items: List<HabitItem>) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitViewHolder.create(inflater, parent, viewType)
    }


    override fun onBindViewHolder(holder: HabitViewHolder?, position: Int) {
        holder?.bind(this.items[position])
    }

    override fun getItemCount(): Int = this.items.size


    internal class HabitViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        // REF https://medium.com/redso/trap-in-kotlin-android-extensions-d07be00759fa
        private val iconImageView: ImageView = itemView.findViewById(R.id.itemImage)
        private val titleTextView : TextView = itemView.findViewById(R.id.itemTitle)
        private val descriptionTextView : TextView = itemView.findViewById(R.id.itemDescription)

        companion object {
            fun create(inflater: LayoutInflater, parent: ViewGroup?, viewType: Int) : HabitViewHolder {
                val itemView = inflater.inflate(R.layout.item_layout, parent, false)
                return HabitViewHolder(itemView)
            }
        }

        internal fun bind(habitItem: HabitItem) {
            iconImageView.setImageResource(habitItem.imageResId)
            titleTextView.text = habitItem.title
            descriptionTextView.text = habitItem.description
        }

    }
}

