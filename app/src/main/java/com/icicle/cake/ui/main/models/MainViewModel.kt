package com.icicle.cake.ui.main.models

import android.content.Intent
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import com.icicle.cake.R
import com.icicle.cake.ui.main.MainActivity
import com.icicle.cake.ui.tutorial.TutorialActivity
import com.icicle.cake.util.QRReader

class MainViewModel(private val activity: MainActivity) {
    val reservationItems = ObservableArrayList<ReservationItem>()
    val isRefreshing = ObservableBoolean().apply { set(false) }

    fun onCameraButtonClick() {
        QRReader.startReader(activity, R.string.scan_qr)
    }

    fun onCodeScanned(content: String) {
        Log.d("testing", content)
    }

    fun onHelpButtonClick() {
        activity.startActivity(Intent(activity, TutorialActivity::class.java))
    }

    fun onRefresh() {
        isRefreshing.set(true)
        isRefreshing.set(false)
    }
}