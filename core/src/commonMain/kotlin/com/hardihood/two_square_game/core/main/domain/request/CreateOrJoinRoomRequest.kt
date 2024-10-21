package com.hardihood.two_square_game.core.main.domain.request

import com.hardihood.two_square_game.core.main.constants.StaticVar
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class CreateOrJoinRoomRequest @OptIn(ExperimentalSerializationApi::class) constructor(
    val boardSize: Int,
    val numOfPlayer: Int,
    @EncodeDefault private val gameVersion: Int = StaticVar.gameVersion,
)