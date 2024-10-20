package com.hardihood.two_square_game.core.main.domain.use_case

import com.hardihood.two_square_game.core.main.domain.model.RoomDataModel
import com.hardihood.two_square_game.core.main.domain.repository.MainRepository
import com.hardihood.two_square_game.core.util.Either
import com.hardihood.two_square_game.main.domain.request.PlayRoomRequest
import io.github.handleerrorapi.Failure
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlayGameUseCase : KoinComponent {
    private val repository: MainRepository by inject()

    @Throws(Exception::class)
    suspend operator fun invoke(
        playRoomRequest: PlayRoomRequest,
    ): Either<Failure, RoomDataModel> {
        return repository.playGame(playRoomRequest)
    }

}