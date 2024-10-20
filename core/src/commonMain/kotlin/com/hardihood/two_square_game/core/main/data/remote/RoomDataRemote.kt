package com.hardihood.two_square_game.core.main.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class RoomDataRemote(
    val board: String? = null,
    @SerialName("yourId")
    val player: Int? = null,
    val id: Int? = null,
)





