package com.hardihood.two_square_game.core.main.domain.model

data class RoomDataModel(
    val id: Int? = null,
    val board: List<Int>? = null,
    val player: Int? = null,
    val message: String? = null,
)