package com.hardihood.two_square_game.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.hardihood.two_square_game.core.models.TypeModeGame

class HomeViewModel : ScreenModel {

    private var adShowed = false

    val typesModeGame = listOf(
        TypeModeGame(
            id = 1,
            name = "Easy"
        ),
        TypeModeGame(
            id = 2,
            name = "Medium"
        ),
        TypeModeGame(
            id = 3,
            name = "Hard"
        )
    )

    var selectedTypeModeGame by mutableStateOf(typesModeGame.first())

    var showPlayWithFriendsQuestion by mutableStateOf(false)
    var showNumOfPlayerQuestion by mutableStateOf(false)
    var playWithFired by mutableStateOf(false)
    var playOnline by mutableStateOf(false)

    var readyForPlay by mutableStateOf(false)


    fun playOffline() {
        playOnline = false
        showPlayWithFriendsQuestion = true
        if (!adShowed) {
            com.hardihood.two_square_game.core.services.AdServices.show()
            adShowed = true
        }
        readyForPlay = true
    }

    fun playOnline() {
        playOnline = true
        // multiplayerViewModel.resetGame()
        if (!adShowed) {
            com.hardihood.two_square_game.core.services.AdServices.show()
            showNumOfPlayerQuestion = true
        }
        readyForPlay = true
    }


    var boardSize: Int = 4

    fun changeMode(typeModeGame: TypeModeGame) {

        selectedTypeModeGame.id = typeModeGame.id
        boardSize = when (typeModeGame.id) {
            1 -> 4
            2 -> 5
            3 -> 6
            // Add more cases for other days
            else -> 4
        }

    }
}