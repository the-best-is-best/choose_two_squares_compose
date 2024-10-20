package com.hardihood.two_square_game.core.main.data.api.ktor_services

import com.hardihood.two_square_game.core.main.constants.ApisConstants
import com.hardihood.two_square_game.core.main.data.api.MainKtorApi
import com.hardihood.two_square_game.core.main.data.response.RoomDataResponse
import com.hardihood.two_square_game.core.main.domain.request.CreateOrJoinRoomRequest
import com.hardihood.two_square_game.main.domain.request.LogoutRoomRequest
import com.hardihood.two_square_game.main.domain.request.PlayRoomRequest
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody


internal class MainKtorServices : MainKtorApi() {
    suspend fun joinOrCreateRoom(createOrJoinRoomRequest: CreateOrJoinRoomRequest): RoomDataResponse =
        client.post {
            pathUrl(ApisConstants.joinOrCreateRoom)
            setBody(createOrJoinRoomRequest)
        }.body()

    suspend fun playGame(playRoomRequest: PlayRoomRequest): RoomDataResponse = client.post {
        pathUrl(ApisConstants.playRoom)
        setBody(playRoomRequest)
    }.body()

    suspend fun getData(id: Int): RoomDataResponse = client.post {
        pathUrl(ApisConstants.getData)
        setBody(hashMapOf("id" to id))
    }.body()

    suspend fun logoutRoom(logoutRoomRequest: LogoutRoomRequest) = client.post {
        pathUrl(ApisConstants.logoutRoom)
        setBody(logoutRoomRequest)
    }

    suspend fun deleteRoom(roomId: Int) = client.delete {
        pathUrl(ApisConstants.deleteRoom)
        setBody(hashMapOf("id" to roomId))
    }
}