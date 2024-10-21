package com.hardihood.two_square_game.multiplayer_screen.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hardihood.two_square_game.core.FontFamilies
import com.hardihood.two_square_game.multiplayer_screen.MultiplayerViewModel
import com.multiplatform.lifecycle.LifecycleEvent
import com.multiplatform.lifecycle.LifecycleObserver
import com.multiplatform.lifecycle.LocalLifecycleTracker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CountdownTimer(
    multiplayerViewModel: MultiplayerViewModel,
) {
    val lifecycleTracker = LocalLifecycleTracker.current
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        val listener =
            object : LifecycleObserver {
                override fun onEvent(event: LifecycleEvent) {
                    println("Lifecycle: onEvent: $event")
                    if (event is LifecycleEvent.OnStartEvent) {
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

//                        if(event is LifecycleEvent.OnDestroyEvent){
//                            viewModel.resetGame()
//
//                        }

                }
            }
        lifecycleTracker.addObserver(listener)
        onDispose {
            lifecycleTracker.removeObserver(listener)
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