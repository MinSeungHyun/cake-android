package com.icicle.cake.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.icicle.cake.R
import com.icicle.cake.databinding.ActivityMainBinding
import com.icicle.cake.ui.main.models.MainViewModel
import com.icicle.cake.util.VerticalSpaceDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { MainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.reservationRecyclerView.apply {
            adapter = ReservationRecyclerAdapter(viewModel)
            reservationRecyclerView.addItemDecoration(VerticalSpaceDecoration(64))
        }
    }
}

