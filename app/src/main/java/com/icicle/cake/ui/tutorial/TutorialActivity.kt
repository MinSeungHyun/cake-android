package com.icicle.cake.ui.tutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.icicle.cake.R
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        viewPager.apply {
            clipToPadding = false
            adapter = TutorialPagerAdapter(this@TutorialActivity)
        }
    }
}