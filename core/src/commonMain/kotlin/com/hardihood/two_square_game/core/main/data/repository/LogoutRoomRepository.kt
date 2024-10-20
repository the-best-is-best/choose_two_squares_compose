package com.hardihood.two_square_game.core.main.data.repository

import com.hardihood.two_square_game.core.main.data.data_source.MainDataSource
import com.hardihood.two_square_game.core.util.Either
import com.hardihood.two_square_game.main.domain.request.LogoutRoomRequest
import io.github.handleerrorapi.Failure
import io.github.handleerrorapi.KtorErrorHandler


internal class LogoutRoomRepository(
    private val remoteDataSource: MainDataSource,
) {
    suspend fun invoke(logoutRoomRequest: LogoutRoomRequest): Either<Failure, Boolean> {
        return try {
            remoteDataSource.logoutRoom(logoutRoomRequest)

            Either.Right(true)
        } catch (e: Exception) {
            Either.Left(KtorErrorHandler().handle(e))
        }
    }
}