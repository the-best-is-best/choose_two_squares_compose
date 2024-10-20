package com.hardihood.two_square_game.core.main.data.repository

import com.hardihood.two_square_game.core.main.data.data_source.MainDataSource
import com.hardihood.two_square_game.core.util.Either
import com.hardihood.two_square_game.core.main.domain.mapper.toModel
import com.hardihood.two_square_game.core.main.domain.model.RoomDataModel
import com.hardihood.two_square_game.main.domain.request.PlayRoomRequest
import io.github.handleerrorapi.Failure
import io.github.handleerrorapi.KtorErrorHandler

internal class PlayGameRepository(
    private val remoteDataSource: MainDataSource,

    ) {
    suspend fun invoke(playRoomRequest: PlayRoomRequest): Either<Failure, RoomDataModel> {
        return try {
            val result = remoteDataSource.playGame(playRoomRequest)

            Either.Right(result.toModel())
        } catch (e: Exception) {
            Either.Left(KtorErrorHandler().handle(e))
        }
    }
}
