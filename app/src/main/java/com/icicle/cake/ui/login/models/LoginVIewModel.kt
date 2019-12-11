package com.icicle.cake.ui.login.models

import android.content.Context
import android.content.Intent
import com.icicle.cake.ui.main.MainActivity

class LoginVIewModel(private val context: Context) {
    fun onLoginButtonClick() {
        context.startActivity(Intent(context, MainActivity::class.java))
    }
}