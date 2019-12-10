package com.icicle.cake.ui.main.models

import android.util.Log
import androidx.databinding.ObservableArrayList
import com.icicle.cake.R
import com.icicle.cake.ui.main.MainActivity
import com.icicle.cake.util.QRReader

class MainViewModel(private val activity: MainActivity) {
    val reservationItems = ObservableArrayList<ReservationItem>()

    fun onCameraButtonClick() {
        QRReader.startReader(activity, R.string.scan_qr)
    }

    fun onCodeScanned(content: String) {
        Log.d("testing", content)
    }
}