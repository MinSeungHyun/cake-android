package com.icicle.cake.ui.login.models

import android.app.Activity
import android.content.Intent
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.icicle.cake.models.CakeUserWithToken
import com.icicle.cake.ui.main.MainActivity
import com.icicle.cake.util.FCMService
import com.icicle.cake.util.SharedPreferenceManager
import com.icicle.cake.util.retrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        attemptLogin()
    }

    private fun attemptLogin() = retrofitService
        .login(hashMapOf("username" to id.get()!!, "password" to password.get()!!))
        .enqueue(object : Callback<CakeUserWithToken> {
            override fun onResponse(call: Call<CakeUserWithToken>, response: Response<CakeUserWithToken>) {
                val result = response.body()
                if (result == null) onLoginFailed()
                else onLoginSuccess(result)
            }

            override fun onFailure(call: Call<CakeUserWithToken>, t: Throwable) {
                t.printStackTrace()
                onLoginFailed()
            }
        })

    private fun onLoginSuccess(result: CakeUserWithToken) {
        val userUid = result.user.uid
        preferenceManager.saveUserIdPw(UserIdPw(id.get()!!, password.get()!!))
        preferenceManager.saveUserUid(userUid)

        FCMService.uploadToken(userUid, preferenceManager.loadFCMToken())

        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
        isLoginProgressive.set(false)
    }

    private fun onLoginFailed() {
        isLoginFailed.set(true)
        isLoginProgressive.set(false)
    }
}