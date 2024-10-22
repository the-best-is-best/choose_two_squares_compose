package com.hardihood.two_square_game.multiplayer_screen.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.hardihood.two_square_game.core.FontFamilies
import com.hardihood.two_square_game.multiplayer_screen.MultiplayerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CountdownTimer(
    multiplayerViewModel: MultiplayerViewModel,
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {

                    coroutineScope.launch {
                        while (multiplayerViewModel.timeStart > 0L) {
                            delay(1000L) // Update every second
                            multiplayerViewModel.timeStart -= 1L
                            if (multiplayerViewModel.timeStart <= 0L) { //
                                // Handle countdown reaching 0 or below
                                multiplayerViewModel.timeOut()
                            }
                        }
                    }

                }

                else -> {}
            }
        }


        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    // Update the Text composable with the current timer value
    Text(
        text = multiplayerViewModel.timeStart.toString(),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontFamily = FontFamilies.getMontserrat()
    )
    Spacer(modifier = Modifier.height(10.dp))
}