package com.icicle.cake.ui.login.models

import android.app.Activity
import android.content.Intent
import androidx.databinding.ObservableBoolean
import com.icicle.cake.ui.main.MainActivity

class LoginVIewModel(private val activity: Activity) {
    val isLoginProgressive = ObservableBoolean().apply { set(false) }

    fun onLoginButtonClick() {
        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
    }
}