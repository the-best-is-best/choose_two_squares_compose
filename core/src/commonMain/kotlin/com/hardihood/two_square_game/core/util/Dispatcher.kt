package com.hardihood.two_square_game.core.util

import kotlinx.coroutines.CoroutineDispatcher

internal interface Dispatcher {
    val io: CoroutineDispatcher
}

internal expect fun providerDispatcher(): Dispatcher