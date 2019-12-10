package com.icicle.cake.ui.tutorial

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.icicle.cake.R
import kotlinx.android.synthetic.main.item_tutorial_card.view.*

class TutorialPagerAdapter(private val context: Context) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        with(inflater.inflate(R.layout.item_tutorial_card, container, false)) {
            tutorialImage.setImageResource(tutorialImageIds[position])
            container.addView(this)
            return this
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return tutorialImageIds.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
}