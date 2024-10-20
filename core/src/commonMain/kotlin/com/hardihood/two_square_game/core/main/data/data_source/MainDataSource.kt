package com.hardihood.two_square_game.core.main.data.data_source

import com.hardihood.two_square_game.core.main.data.api.ktor_services.MainKtorServices
import com.hardihood.two_square_game.core.util.Dispatcher
import com.hardihood.two_square_game.core.main.domain.request.CreateOrJoinRoomRequest
import com.hardihood.two_square_game.main.domain.request.LogoutRoomRequest
import com.hardihood.two_square_game.main.domain.request.PlayRoomRequest
import kotlinx.coroutines.withContext

internal class MainDataSource(
    private val ktorService: MainKtorServices,
    private val dispatcher: Dispatcher,
) {
    suspend fun createOrJoinRequest(createOrJoinRoomRequest: CreateOrJoinRoomRequest) =
        withContext(dispatcher.io) {
            ktorService.joinOrCreateRoom(createOrJoinRoomRequest)

        }

    suspend fun playGame(playRoomRequest: PlayRoomRequest) = withContext(dispatcher.io) {
        ktorService.playGame(playRoomRequest)
    }

    suspend fun getData(id: Int) = withContext(dispatcher.io) {
        ktorService.getData(id)
    }


    suspend fun logoutRoom(logoutRoomRequest: LogoutRoomRequest) = withContext(dispatcher.io) {
        ktorService.logoutRoom(logoutRoomRequest)
    }

    suspend fun deleteRoom(roomId: Int) = withContext(dispatcher.io) {
        ktorService.deleteRoom(roomId)
    }

}