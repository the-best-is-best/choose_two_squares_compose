package com.hardihood.two_square_game.core.main.data.repository

import com.hardihood.two_square_game.core.main.data.data_source.MainDataSource
import com.hardihood.two_square_game.core.util.Either
import io.github.handleerrorapi.Failure
import io.github.handleerrorapi.KtorErrorHandler


internal class DeleteRoomRepository(
    private val remoteDataSource: MainDataSource,
) {
    suspend fun invoke(roomId: Int): Either<Failure, Boolean> {
        return try {
            remoteDataSource.deleteRoom(roomId)

            Either.Right(true)
        } catch (e: Exception) {
            Either.Left(KtorErrorHandler().handle(e))
        }
    }
}