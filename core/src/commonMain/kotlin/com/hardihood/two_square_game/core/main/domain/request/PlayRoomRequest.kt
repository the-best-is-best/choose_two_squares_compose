package com.hardihood.two_square_game.main.domain.request

import kotlinx.serialization.Serializable

@Serializable
data class PlayRoomRequest(
    val roomId: Int,
    val playerId: Int,
    val number1: Int,
    val number2: Int,
)