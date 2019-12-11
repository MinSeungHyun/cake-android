package com.icicle.cake.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.zxing.integration.android.IntentIntegrator
import com.icicle.cake.R
import com.icicle.cake.databinding.ActivityMainBinding
import com.icicle.cake.ui.main.models.MainViewModel
import com.icicle.cake.ui.tutorial.TutorialActivity
import com.icicle.cake.util.SharedPreferenceManager
import com.icicle.cake.util.VerticalSpaceDecoration
import com.icicle.cake.util.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this, ViewModelFactory(this))[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )
        binding.reservationRecyclerView.apply {
            adapter = ReservationRecyclerAdapter(viewModel)
            reservationRecyclerView.addItemDecoration(VerticalSpaceDecoration(64))
        }
        binding.vm = viewModel

        if (isTutorialNotSaw())
            startActivity(Intent(this, TutorialActivity::class.java))
    }

    private fun isTutorialNotSaw() = !SharedPreferenceManager(this).loadIsTutorialSaw()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            ?: return super.onActivityResult(requestCode, resultCode, data)
        result.contents?.let {
            viewModel.onCodeScanned(result.contents)
        }
    }
}

