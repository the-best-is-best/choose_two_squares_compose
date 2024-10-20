package com.hardihood.two_square_game.core.main.data.response

import com.hardihood.two_square_game.core.main.data.remote.RoomDataRemote
import kotlinx.serialization.Serializable

@Serializable
internal data class RoomDataResponse(
    override val messages: List<String>?,
    override val success: Boolean?,
    override val data: RoomDataRemote? = null,


    ) : BaseResponse<RoomDataRemote?>()
