package com.icicle.cake.ui.main.models

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.icicle.cake.R
import com.icicle.cake.ui.login.LoginActivity
import com.icicle.cake.ui.tutorial.TutorialActivity
import com.icicle.cake.util.QRReader

class MainViewModel(private val activity: Activity) : ViewModel() {
    val reservationItems = ObservableArrayList<ReservationItem>()
    val isRefreshing = ObservableBoolean().apply { set(false) }

    private val reference = FirebaseDatabase.getInstance().reference.child("rooms")
    private val roomChildEventListener = RoomChildEventListener()

    init {
        startDoorOpenListener()
    }

    fun onCameraButtonClick() {
        QRReader.startReader(activity, R.string.scan_qr)
    }

    fun onCodeScanned(content: String) {
    }

    fun onHelpButtonClick() {
        activity.startActivity(Intent(activity, TutorialActivity::class.java))
    }

    fun onLogoutButtonClick() {
        AlertDialog.Builder(activity)
            .setTitle(R.string.logout_check)
            .setPositiveButton(R.string.logout) { _, _ -> logout() }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    fun onRefresh() {
        isRefreshing.set(true)
        isRefreshing.set(false)
    }

    private fun logout() {
        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity.finish()
    }

    private fun startDoorOpenListener() {
        reference.addChildEventListener(roomChildEventListener)
    }

    private fun stopDoorOpenListener() {
        reference.removeEventListener(roomChildEventListener)
    }

    override fun onCleared() {
        super.onCleared()
        stopDoorOpenListener()
    }

    private fun showDoorDialog(isOpened: Boolean) {
        val stringRes = if (isOpened) R.string.door_opened else R.string.door_closed
        AlertDialog.Builder(activity)
            .setTitle(stringRes)
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    inner class RoomChildEventListener : ChildEventListener {
        override fun onChildChanged(data: DataSnapshot, previousChildName: String?) {
            if (data.value.toString().toBoolean()) { //Door opened
                val roomID = data.key
                showDoorDialog(true)
            } else { //Door closed
                showDoorDialog(false)
            }
        }

        override fun onCancelled(p0: DatabaseError) {}
        override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
        override fun onChildAdded(p0: DataSnapshot, p1: String?) {}
        override fun onChildRemoved(p0: DataSnapshot) {}
    }
}