package com.example.number

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class NumberAdapter(private var items: List<Int> = emptyList()) :
    RecyclerView.Adapter<NumberAdapter.NumberVH>() {

    fun submit(data: List<Int>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_number, parent, false)
        return NumberVH(view)
    }

    override fun onBindViewHolder(holder: NumberVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class NumberVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btn: Button = itemView.findViewById(R.id.btnNumber)
        fun bind(value: Int) {
            btn.text = value.toString()
        }
    }
}
