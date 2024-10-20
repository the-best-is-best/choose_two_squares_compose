package com.hardihood.two_square_game.core

import com.hardihood.two_square_game.game_screen.GameViewModel
import com.hardihood.two_square_game.home_screen.HomeViewModel
import com.hardihood.two_square_game.multiplayer_screen.MultiplayerViewModel
import org.koin.dsl.module


val appModules = module {
    factory { HomeViewModel() }
    factory { GameViewModel() }
    factory { MultiplayerViewModel(get(), get(), get(), get(), get()) }

}