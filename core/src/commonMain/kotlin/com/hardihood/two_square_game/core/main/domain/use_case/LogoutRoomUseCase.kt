package com.hardihood.two_square_game.core.main.domain.use_case

import com.hardihood.two_square_game.core.main.domain.repository.MainRepository
import com.hardihood.two_square_game.core.util.Either
import com.hardihood.two_square_game.main.domain.request.LogoutRoomRequest
import io.github.handleerrorapi.Failure
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class LogoutRoomUseCase : KoinComponent {
    private val repository: MainRepository by inject()

    @Throws(Exception::class)
    suspend operator fun invoke(logoutRoomRequest: LogoutRoomRequest): Either<Failure, Boolean> {
        return repository.logoutRoom(logoutRoomRequest)
    }

}