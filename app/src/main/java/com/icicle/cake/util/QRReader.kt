package com.icicle.cake.util

import android.app.Activity
import androidx.annotation.StringRes
import com.google.zxing.integration.android.IntentIntegrator

class QRReader {
    companion object {
        fun startReader(activity: Activity, @StringRes description: Int? = null) {
            val reader = IntentIntegrator(activity)
            if (description != null) reader.setPrompt(activity.getString(description))
            reader.setOrientationLocked(false)
            reader.initiateScan()
        }
    }
}