package com.icicle.cake.models

data class CakeUserWithToken(
    val user: CakeUser,
    val token: String
)