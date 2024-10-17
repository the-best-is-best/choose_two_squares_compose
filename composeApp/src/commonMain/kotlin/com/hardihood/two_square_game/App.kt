package com.hardihood.two_square_game

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.hardihood.two_square_game.core.AppColors
import com.hardihood.two_square_game.splash_screen.SplashScreen
import com.hardihood.two_square_game.theme.AppTheme

@Composable
internal fun App() = AppTheme(useDarkTheme = false) {
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier.fillMaxSize()

            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .navigationBarsPadding()
            .imePadding(),
        color = AppColors.mainColor,
    ) {
        MaterialTheme(
            colorScheme = lightColorScheme()
        ) {
            CompositionLocalProvider {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.widthIn(min = 500.dp, max = 800.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {
                        Navigator(SplashScreen())

                    }
                }

            }
        }
    }

}
