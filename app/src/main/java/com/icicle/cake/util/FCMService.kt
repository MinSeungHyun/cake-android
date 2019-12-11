package com.icicle.cake.util

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService

class FCMService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        startTokenUpdateProgress(token, applicationContext)
    }

    companion object {
        fun startTokenListener(context: Context) {
            FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
                if (it.result == null) return@addOnCompleteListener
                startTokenUpdateProgress(it.result!!.token, context)
            }
        }

        fun uploadToken(userIdx: String, token: String) {
            val reference = FirebaseDatabase.getInstance().getReference("users")
            reference.child(userIdx).setValue(token)
        }

        private fun startTokenUpdateProgress(token: String, context: Context) {
            saveToken(token, context)
            val userIdx = getUserIdxIfAvailable(context)
            if (userIdx.isNotBlank()) uploadToken(userIdx, token)
        }

        private fun saveToken(token: String, context: Context) {
            val preferenceManager = SharedPreferenceManager(context)
            preferenceManager.saveFCMToken(token)
        }

        private fun getUserIdxIfAvailable(context: Context): String {
            val preferenceManager = SharedPreferenceManager(context)
            val userIdx = preferenceManager.loadUserIdx()
            val password = preferenceManager.loadUserIdPw().pw
            return if (userIdx.isNotBlank() && password.isNotBlank()) userIdx
            else ""
        }
    }
}