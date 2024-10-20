package com.hardihood.two_square_game.core.main.domain.repository

import com.hardihood.two_square_game.core.main.domain.model.RoomDataModel
import com.hardihood.two_square_game.core.util.Either
import com.hardihood.two_square_game.core.main.domain.request.CreateOrJoinRoomRequest
import com.hardihood.two_square_game.main.domain.request.LogoutRoomRequest
import com.hardihood.two_square_game.main.domain.request.PlayRoomRequest
import io.github.handleerrorapi.Failure

internal interface MainRepository {
    suspend fun createOrJoinRoom(createOrJoinRoomRequest: CreateOrJoinRoomRequest): Either<Failure, RoomDataModel>

    suspend fun playGame(playRoomRequest: PlayRoomRequest): Either<Failure, RoomDataModel>

    suspend fun getDataRoom(id: Int): Either<Failure, RoomDataModel>

    suspend fun logoutRoom(logoutRoomRequest: LogoutRoomRequest): Either<Failure, Boolean>

    suspend fun deleteRoom(roomId: Int): Either<Failure, Boolean>
}
