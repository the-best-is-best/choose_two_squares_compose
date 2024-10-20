package com.hardihood.two_square_game.core.di

import com.hardihood.two_square_game.core.util.providerDispatcher
import com.hardihood.two_square_game.core.main.di.mainSharedModules
import org.koin.dsl.module


private val utilityModule = module {
    factory { providerDispatcher() }
}

private val sharedModules = listOf(utilityModule) + mainSharedModules()

fun getSharedModules() = sharedModules