package com.hardihood.two_square_game.main.domain.request

import kotlinx.serialization.Serializable

@Serializable
data class LogoutRoomRequest(
    val roomId: Int,
    val userId: Int,

    )