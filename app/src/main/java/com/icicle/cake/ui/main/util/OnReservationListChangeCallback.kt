package com.icicle.cake.ui.main.util

import androidx.databinding.ObservableList
import com.icicle.cake.ui.main.ReservationRecyclerAdapter
import com.icicle.cake.ui.main.models.ReservationItem

class OnReservationListChangeCallback(private val adapter: ReservationRecyclerAdapter) : ObservableList.OnListChangedCallback<ObservableList<ReservationItem>>() {
    override fun onItemRangeInserted(sender: ObservableList<ReservationItem>?, positionStart: Int, itemCount: Int) {
        adapter.notifyItemRangeInserted(positionStart, itemCount)
    }

    override fun onItemRangeRemoved(sender: ObservableList<ReservationItem>?, positionStart: Int, itemCount: Int) {
        adapter.notifyItemRangeRemoved(positionStart, itemCount)
    }

    override fun onItemRangeChanged(sender: ObservableList<ReservationItem>?, positionStart: Int, itemCount: Int) {
        adapter.notifyItemRangeChanged(positionStart, itemCount)
    }

    override fun onChanged(sender: ObservableList<ReservationItem>?) {}
    override fun onItemRangeMoved(sender: ObservableList<ReservationItem>?, fromPosition: Int, toPosition: Int, itemCount: Int) {}
}