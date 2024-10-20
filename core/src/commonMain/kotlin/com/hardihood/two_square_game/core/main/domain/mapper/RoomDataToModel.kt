package com.hardihood.two_square_game.core.main.domain.mapper

import com.hardihood.two_square_game.core.main.data.response.RoomDataResponse
import com.hardihood.two_square_game.core.main.domain.model.RoomDataModel


internal fun RoomDataResponse.toModel(): RoomDataModel {
    val dataBoard = data?.board?.replace("[", "")?.replace("]", "")?.split(",")

    return RoomDataModel(
        data?.id,
        if (data?.board == null) null else dataBoard?.map { it.trim().toInt() },
        data?.player,
        messages?.first()

    )


}