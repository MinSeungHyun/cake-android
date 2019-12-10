package com.icicle.cake.ui.tutorial

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.icicle.cake.R
import kotlinx.android.synthetic.main.activity_tutorial.*

val tutorialImageIds = listOf(
    R.drawable.tutorial_1,
    R.drawable.tutorial_2,
    R.drawable.tutorial_3,
    R.drawable.tutorial_4,
    R.drawable.tutorial_5,
    R.drawable.tutorial_6
)

class TutorialActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        viewPager.apply {
            clipToPadding = false
            adapter = TutorialPagerAdapter(this@TutorialActivity)
            addOnPageChangeListener(this@TutorialActivity)
        }

        startButton.setOnClickListener { finish() }
    }

    override fun onPageSelected(position: Int) {
        if (position == tutorialImageIds.size - 1) startButton.visibility = View.VISIBLE
        else startButton.visibility = View.INVISIBLE
    }

    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
}