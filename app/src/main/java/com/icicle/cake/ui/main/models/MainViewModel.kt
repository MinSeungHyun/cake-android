package com.icicle.cake.ui.main.models

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.icicle.cake.R
import com.icicle.cake.models.CakeRoom
import com.icicle.cake.models.CakeRooms
import com.icicle.cake.ui.login.LoginActivity
import com.icicle.cake.ui.tutorial.TutorialActivity
import com.icicle.cake.util.QRReader
import com.icicle.cake.util.SharedPreferenceManager
import com.icicle.cake.util.retrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val activity: Activity) : ViewModel() {
    val reservationItems = ObservableArrayList<ReservationItem>()
    val isRefreshing = ObservableBoolean().apply { set(false) }

    private val reference = FirebaseDatabase.getInstance().reference.child("rooms")
    private val roomChildEventListener = RoomChildEventListener()
    private val preferenceManager = SharedPreferenceManager(activity)

    init {
        loadUserRooms()
        startDoorOpenListener()
    }

    fun onCameraButtonClick() {
        QRReader.startReader(activity, R.string.scan_qr)
    }

    fun onCodeScanned(content: String) {
        val uid = preferenceManager.loadUserUid()
        retrofitService.postScannedQR(hashMapOf("room" to content, "user" to uid))
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() != 200) showDoorDialog(DoorRequestState.ERROR)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    t.printStackTrace()
                    showDoorDialog(DoorRequestState.ERROR)
                }
            })
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
        loadUserRooms()
    }

    private fun loadUserRooms() {
        val token = preferenceManager.loadToken()
        retrofitService.getRooms("Bearer $token")
            .enqueue(object : Callback<CakeRooms> {
                override fun onResponse(call: Call<CakeRooms>, response: Response<CakeRooms>) {
                    val result = response.body()
                    if (result?.rooms == null) onLoadRoomsFailed()
                    else onLoadRoomsSuccess(result)
                }

                override fun onFailure(call: Call<CakeRooms>, t: Throwable) {
                    t.printStackTrace()
                    onLoadRoomsFailed()
                }
            })
    }

    private fun onLoadRoomsSuccess(result: CakeRooms) {
        reservationItems.clear()
        result.rooms.forEach {
            val date = getDateFromTimestamp(it.date.toLong())
            val timeString = getTimeString(it)
            val userString = getUserString(it)
            val item = ReservationItem(date, timeString, it.room, it.desc, userString)
            reservationItems.add(item)
        }
        isRefreshing.set(false)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateFromTimestamp(timeStamp: Long): String {
        val date = Date(timeStamp)
        val dateFormat = SimpleDateFormat("yyyy / MM / dd")
        return dateFormat.format(date)
    }

    private fun getTimeString(cakeRoom: CakeRoom): String {
        val timeString = StringBuilder()
        cakeRoom.times.forEachIndexed { index, time ->
            timeString.append(time)
            if (index != cakeRoom.times.size - 1)
                timeString.append("\n")
        }
        return timeString.toString()
    }

    private fun getUserString(cakeRoom: CakeRoom): String {
        val userString = StringBuilder()
        cakeRoom.users.forEachIndexed { index, roomUser ->
            userString.append(roomUser.name)
            if (index != cakeRoom.users.size - 1)
                userString.append(", ")
        }
        return userString.toString()
    }

    private fun onLoadRoomsFailed() {
        Toast.makeText(activity, R.string.load_rooms_failed, Toast.LENGTH_LONG).show()
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

    private fun showDoorDialog(status: DoorRequestState) {
        val stringRes = when (status) {
            DoorRequestState.OPENED -> R.string.door_opened
            DoorRequestState.CLOSED -> R.string.door_closed
            else -> R.string.qr_post_failed
        }
        AlertDialog.Builder(activity)
            .setTitle(stringRes)
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    inner class RoomChildEventListener : ChildEventListener {
        override fun onChildChanged(data: DataSnapshot, previousChildName: String?) {
            if (data.value.toString().toBoolean()) {
                val roomID = data.key
                reservationItems.forEach {
                    //if(it.roomName)
                }
                showDoorDialog(DoorRequestState.OPENED)
            } else {
                showDoorDialog(DoorRequestState.CLOSED)
            }
        }

        override fun onCancelled(p0: DatabaseError) {}
        override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
        override fun onChildAdded(p0: DataSnapshot, p1: String?) {}
        override fun onChildRemoved(p0: DataSnapshot) {}
    }

    private enum class DoorRequestState { OPENED, CLOSED, ERROR }
}