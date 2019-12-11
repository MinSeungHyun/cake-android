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

        private fun startTokenUpdateProgress(token: String, context: Context) {
            saveToken(token, context)
            uploadToken(token)

        }

        private fun saveToken(token: String, context: Context) {
            val preferenceManager = SharedPreferenceManager(context)
            preferenceManager.saveFCMToken(token)
        }

        private fun uploadToken(token: String) {
            val reference = FirebaseDatabase.getInstance().getReference("users")
            reference.child("1696").setValue(token)
        }
    }
}