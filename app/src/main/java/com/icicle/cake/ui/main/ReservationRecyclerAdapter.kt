package com.icicle.cake.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icicle.cake.databinding.ItemReservationCardBinding
import com.icicle.cake.ui.main.models.MainViewModel
import com.icicle.cake.ui.main.models.ReservationItem
import com.icicle.cake.ui.main.util.OnReservationListChangeCallback

class ReservationRecyclerAdapter(viewModel: MainViewModel) : RecyclerView.Adapter<ReservationRecyclerAdapter.ReservationViewHolder>() {
    private val items = viewModel.reservationItems

    init {
        items.addOnListChangedCallback(OnReservationListChangeCallback(this))
    }

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

    class ReservationViewHolder(private val binding: ItemReservationCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReservationItem) {
            binding.item = item
        }
    }
}