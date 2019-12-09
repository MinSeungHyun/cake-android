package com.icicle.cake.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icicle.cake.databinding.ItemReservationCardBinding
import com.icicle.cake.ui.main.models.ReservationItem

class ReservationRecyclerAdapter : RecyclerView.Adapter<ReservationRecyclerAdapter.ReservationViewHolder>() {
    private var items = ArrayList<ReservationItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = ItemReservationCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: ReservationItem, position: Int = items.size) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    class ReservationViewHolder(private val binding: ItemReservationCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReservationItem) {
            binding.item = item
        }
    }
}