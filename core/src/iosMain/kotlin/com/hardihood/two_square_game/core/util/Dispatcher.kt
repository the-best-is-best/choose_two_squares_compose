package com.hardihood.two_square_game.core.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


internal class IosDispatcher : Dispatcher {
    override val io: CoroutineDispatcher
        get() = Dispatchers.Default

}

internal actual fun providerDispatcher(): Dispatcher = IosDispatcher()