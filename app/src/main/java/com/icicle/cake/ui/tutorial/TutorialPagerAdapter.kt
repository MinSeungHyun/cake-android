package com.icicle.cake.ui.tutorial

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.icicle.cake.databinding.ItemTutorialCardBinding

class TutorialPagerAdapter(private val context: Context) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        with(ItemTutorialCardBinding.inflate(inflater, container, false)) {
            tutorialImage.setImageResource(tutorialImageIds[position])
            container.addView(root)
            return root
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
