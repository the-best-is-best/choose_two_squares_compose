package com.hardihood.two_square_game.core.main.domain.use_case

import com.hardihood.two_square_game.core.main.domain.repository.MainRepository
import com.hardihood.two_square_game.core.util.Either
import io.github.handleerrorapi.Failure
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DeleteRoomUseCase : KoinComponent {
    private val repository: MainRepository by inject()

    @Throws(Exception::class)
    suspend operator fun invoke(
        roomId: Int,
    ): Either<Failure, Boolean> {
        return repository.deleteRoom(roomId)
    }

}