package com.icicle.cake.ui.login.models

import android.app.Activity
import android.content.Intent
import com.icicle.cake.ui.main.MainActivity

class LoginVIewModel(private val activity: Activity) {
    fun onLoginButtonClick() {
        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
    }
}