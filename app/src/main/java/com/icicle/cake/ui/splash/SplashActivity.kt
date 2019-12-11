package com.icicle.cake.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.icicle.cake.models.CakeUserWithToken
import com.icicle.cake.ui.login.LoginActivity
import com.icicle.cake.ui.main.MainActivity
import com.icicle.cake.util.FCMService
import com.icicle.cake.util.SharedPreferenceManager
import com.icicle.cake.util.retrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    private val preferenceManager = SharedPreferenceManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FCMService.startTokenListener(this)
        attemptLogin()
    }

    private fun attemptLogin() {
        val userIdPw = preferenceManager.loadUserIdPw()
        retrofitService.login(hashMapOf("username" to userIdPw.id, "password" to userIdPw.pw))
            .enqueue(object : Callback<CakeUserWithToken> {
                override fun onResponse(call: Call<CakeUserWithToken>, response: Response<CakeUserWithToken>) {
                    val result = response.body()
                    if (result?.token != null) onLoginSuccess()
                    else onLoginFailed()
                }

                override fun onFailure(call: Call<CakeUserWithToken>, t: Throwable) {
                    t.printStackTrace()
                    onLoginFailed()
                }
            })
    }

    private fun onLoginSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun onLoginFailed() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}