package com.hardihood.two_square_game.core

import com.hardihood.two_square_game.home_screen.HomeViewModel
import org.koin.dsl.module


val appModules = module {
    factory { HomeViewModel() }

}