package com.icicle.cake.models

data class CakeRoom(
    val times: ArrayList<String>,
    val users: ArrayList<RoomUser>,
    val desc: String,
    val max: Int,
    val name: String,
    val room: String,
    val date: String,
    val id: String
)