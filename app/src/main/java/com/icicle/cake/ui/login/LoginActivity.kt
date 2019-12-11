package com.icicle.cake.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.icicle.cake.R
import com.icicle.cake.databinding.ActivityLoginBinding
import com.icicle.cake.ui.login.models.LoginVIewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel by lazy { LoginVIewModel(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login).apply {
            vm = viewModel
        }
    }
}