package com.icicle.cake.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.icicle.cake.R
import com.icicle.cake.databinding.ActivityMainBinding
import com.icicle.cake.util.VerticalSpaceDecoration

class MainActivity : AppCompatActivity() {
    private val recyclerViewAdapter by lazy { ReservationRecyclerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            reservationRecyclerView.adapter = recyclerViewAdapter
            reservationRecyclerView.addItemDecoration(VerticalSpaceDecoration(64))
        }
    }
}

