package com.icicle.cake.util

import android.content.Context
import com.icicle.cake.ui.login.models.UserIdPw

private const val PREFERENCE_NAME = "app_preference"

class SharedPreferenceManager(private val context: Context) {
    private val preference by lazy {
        context.getSharedPreferences(
            PREFERENCE_NAME, Context.MODE_PRIVATE
        )
    }
    private val preferenceEdit by lazy { preference.edit() }

    fun saveUserIdPw(userIdPw: UserIdPw) {
        preferenceEdit.apply {
            putString("id", userIdPw.id)
            putString("pw", userIdPw.pw)
            apply()
        }
    }

    fun loadUserIdPw(): UserIdPw {
        val id = preference.getString("id", "") ?: ""
        val pw = preference.getString("pw", "") ?: ""
        return UserIdPw(id, pw)
    }

    fun saveFCMToken(token: String) {
        preferenceEdit.apply {
            putString("fcm_token", token)
            apply()
        }
    }

    fun loadFCMToken(): String = preference.getString("fcm_token", "") ?: ""

    fun saveUserUid(userUid: String) {
        preferenceEdit.apply {
            putString("user_uid", userUid)
            apply()
        }
    }

    fun loadUserUid(): String = preference.getString("user_uid", "") ?: ""

    fun saveIsTutorialSaw(isTutorialSaw: Boolean) {
        preferenceEdit.apply {
            putBoolean("is_tutorial_saw", isTutorialSaw)
            apply()
        }
    }

    fun loadIsTutorialSaw() = preference.getBoolean("is_tutorial_saw", false)
}