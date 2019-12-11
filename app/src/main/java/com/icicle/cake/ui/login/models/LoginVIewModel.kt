package com.icicle.cake.ui.login.models

import android.app.Activity
import android.content.Intent
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.icicle.cake.ui.main.MainActivity
import com.icicle.cake.util.FCMService
import com.icicle.cake.util.SharedPreferenceManager

class LoginVIewModel(private val activity: Activity) {
    val isLoginProgressive = ObservableBoolean().apply { set(false) }
    val isLoginFailed = ObservableBoolean().apply { set(false) }
    val id = ObservableField<String>()
    val password = ObservableField<String>()

    private val preferenceManager = SharedPreferenceManager(activity)

    init {
        val userIdPw = preferenceManager.loadUserIdPw()
        id.set(userIdPw.id)
        password.set(userIdPw.pw)
    }

    fun onLoginButtonClick() {
        isLoginProgressive.set(true)
        isLoginFailed.set(false)
        Thread {
            Thread.sleep(1000) //loginProgress
            isLoginProgressive.set(false)
            onLoginSuccess()
        }.start()
    }

    private fun onLoginSuccess() {
        FCMService.uploadToken("1696", preferenceManager.loadFCMToken())
        preferenceManager.saveUserIdPw(UserIdPw(id.get()!!, password.get()!!))
        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
    }

    private fun onLoginFailed() {
        isLoginFailed.set(true)
    }
}