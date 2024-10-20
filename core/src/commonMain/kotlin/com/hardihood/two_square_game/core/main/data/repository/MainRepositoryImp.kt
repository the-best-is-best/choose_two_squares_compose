package com.hardihood.two_square_game.core.main.data.repository

import com.hardihood.two_square_game.core.util.Either
import com.hardihood.two_square_game.core.main.domain.model.RoomDataModel
import com.hardihood.two_square_game.core.main.domain.repository.MainRepository
import com.hardihood.two_square_game.core.main.domain.request.CreateOrJoinRoomRequest
import com.hardihood.two_square_game.main.domain.request.LogoutRoomRequest
import com.hardihood.two_square_game.main.domain.request.PlayRoomRequest
import io.github.handleerrorapi.Failure

internal class MainRepositoryImp(
    private val createOrJoinRoomRepository: CreateOrJoinRoomRepository,
    private val playGameRepository: PlayGameRepository,
    private val getDataRoomRepository: GetDataRoomRepository,
    private val logoutRoomRepository: LogoutRoomRepository,
    private val deleteRoomRepository: DeleteRoomRepository,
) : MainRepository {

    override suspend fun createOrJoinRoom(createOrJoinRoomRequest: CreateOrJoinRoomRequest) =
        createOrJoinRoomRepository.invoke(createOrJoinRoomRequest)

    override suspend fun playGame(playRoomRequest: PlayRoomRequest): Either<Failure, RoomDataModel> {
        return playGameRepository.invoke(playRoomRequest)
    }

    override suspend fun getDataRoom(id: Int): Either<Failure, RoomDataModel> {
        return getDataRoomRepository.invoke(id)
    }

    override suspend fun logoutRoom(logoutRoomRequest: LogoutRoomRequest): Either<Failure, Boolean> =
        logoutRoomRepository.invoke(logoutRoomRequest)

    override suspend fun deleteRoom(roomId: Int): Either<Failure, Boolean> =
        deleteRoomRepository.invoke(roomId)

}