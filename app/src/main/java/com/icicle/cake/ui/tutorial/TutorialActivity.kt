package com.icicle.cake.ui.tutorial

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.icicle.cake.R
import com.icicle.cake.databinding.ActivityTutorialBinding
import com.icicle.cake.util.SharedPreferenceManager

val tutorialImageIds = listOf(
    R.drawable.tutorial_1,
    R.drawable.tutorial_2,
    R.drawable.tutorial_3,
    R.drawable.tutorial_4, R.drawable.tutorial_5
)

class TutorialActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    private val binding by lazy { ActivityTutorialBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.viewPager.apply {
            clipToPadding = false
            adapter = TutorialPagerAdapter(this@TutorialActivity)
            addOnPageChangeListener(this@TutorialActivity)
        }

        binding.startButton.setOnClickListener {
            saveTutorialSaw()
            finish()
        }
    }

    private fun saveTutorialSaw() = SharedPreferenceManager(this).saveIsTutorialSaw(true)

    override fun onPageSelected(position: Int) {
        if (position == tutorialImageIds.size - 1) binding.startButton.visibility = View.VISIBLE
        else binding.startButton.visibility = View.INVISIBLE
    }

    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
}
