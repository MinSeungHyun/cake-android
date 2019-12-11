package com.icicle.cake.util

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.icicle.cake.ui.main.models.MainViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val activity: Activity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(activity) as T
    }
}