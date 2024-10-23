package com.hardihood.two_square_game.multiplayer_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import choose_two_squares.composeapp.generated.resources.Res
import choose_two_squares.composeapp.generated.resources.game_draw
import choose_two_squares.composeapp.generated.resources.game_name
import choose_two_squares.composeapp.generated.resources.room_error
import choose_two_squares.composeapp.generated.resources.serverError
import choose_two_squares.composeapp.generated.resources.wait_for_players
import choose_two_squares.composeapp.generated.resources.you_cannot_play_here
import com.hardihood.two_square_game.components.MyText
import com.hardihood.two_square_game.components.MyTextAttribute
import com.hardihood.two_square_game.components.ShowDialog
import com.hardihood.two_square_game.components.ShowErrorDialogApi
import com.hardihood.two_square_game.core.AppColors
import com.hardihood.two_square_game.core.FontFamilies
import com.hardihood.two_square_game.core.services.AdServices
import com.hardihood.two_square_game.home_screen.HomeScreen
import com.hardihood.two_square_game.multiplayer_screen.component.CountdownTimer
import io.github.kadmob.model.KAdmobBannerType
import io.github.kadmob.views.KBannerAd
import io.github.tbib.compose_toast.AdvToast
import io.github.tbib.compose_toast.rememberAdvToastStates
import io.github.tbib.compose_toast.toast_ui.EnumToastType
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

data class MultiplayerScreen(
    val boardSize: Int,
    val numberOfPlayer: Int,
) : Screen {
    @Composable
    override fun Content() {
        val lifecycleOwner: LifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
        val navigator = LocalNavigator.currentOrThrow

        val viewModel: MultiplayerViewModel = koinScreenModel()
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_STOP, Lifecycle.Event.ON_PAUSE -> {
                        if (viewModel.idRoom != null) {
                            viewModel.logout()

                        }

                    }

                    else -> {

                    }
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
        var gameStart by remember { mutableStateOf(false) }

        LaunchedEffect(!gameStart) {
            if (!gameStart) {
                gameStart = true
                viewModel.startGame(boardSize, numberOfPlayer)
            }
        }

        val stateErrorToast = rememberAdvToastStates()

        val coroutineScope = rememberCoroutineScope()
        val youCannotPlayHereText = stringResource(Res.string.you_cannot_play_here)

        AdvToast.MakeToast(
            state = stateErrorToast,
            toastType = EnumToastType.ERROR,
            paddingBottom = 50
        )
        val state = viewModel.state.collectAsState()
        if (state.value.errorMessage?.messages?.contains("You can't play here") == true) {
            coroutineScope.launch {
                viewModel.state.value = viewModel.state.value.copy(
                    errorMessage = null
                )

                stateErrorToast.show(
                    youCannotPlayHereText
                )
            }

        } else if (state.value.errorMessage?.messages?.contains("Please Update Game") == true) {

            ShowErrorDialogApi(
                visible = viewModel.showDialog,
                message = state.value.errorMessage!!,
                cancel = {
                    coroutineScope.launch {
                        viewModel.showDialog = false
                    }

                    navigator.replaceAll(HomeScreen())


                },

                )
        } else if (state.value.errorMessage?.messages?.contains("No one WIN the game") == true) {
            AdServices.showInterstitialAd()

            coroutineScope.launch {
                viewModel.deleteRoom()
            }
            ShowDialog(message = stringResource(Res.string.room_error), onClick = {

                navigator.replaceAll(HomeScreen())


            })
        } else if (viewModel.endGame) {
            AdServices.showInterstitialAd()

            coroutineScope.launch {
                viewModel.deleteRoom()
            }
            if (!viewModel.gameStarted) {
                navigator.replaceAll(HomeScreen())

            } else {

                val info = if (viewModel.playerLost == null
                ) {
                    AdServices.showInterstitialAd()

                    if (viewModel.playerWin == 0
                    ) "No One Win The Game"
                    else if (viewModel.playerWin == null || viewModel.playerWin == -1
                    ) "Room ended"
                    else if (viewModel.player == viewModel.playerWin
                    ) "You Win The Game"
                    else "You Lose The Game"

                } else {
                    if (viewModel.player != viewModel.playerLost
                    ) "You Win The Game"
                    else "You Lose The Game"
                }
                ShowDialog(message = info, onClick = {

                navigator.replaceAll(HomeScreen())
                })
            }
        } else if (viewModel.roomError) {
            AdServices.showInterstitialAd()

            ShowDialog(message = stringResource(Res.string.room_error), onClick = {

            navigator.replaceAll(HomeScreen())

            })
        } else if (viewModel.gameDraw) {
            AdServices.showInterstitialAd()

            ShowDialog(message = stringResource(Res.string.game_draw), onClick = {

            navigator.replaceAll(HomeScreen())
            })

        } else {
            if (state.value.errorMessage != null && state.value.errorMessage!!.statusCode != null && state.value.errorMessage!!.statusCode!! >= 400) {
                ShowDialog(
                    message = state.value.errorMessage!!.messages
                        ?: stringResource(Res.string.serverError), onClick = {

                    navigator.replaceAll(HomeScreen())
                    })
            }
        }


        if (viewModel.board.isEmpty() || !viewModel.gameStarted) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppColors.secondColor)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            viewModel.logout()
                        }


                    }) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "new",
                            tint = Color.White
                        )

                    }
                    Spacer(modifier = Modifier.width(25.dp))
                    MyText(
                        attribute = MyTextAttribute(
                            textAlign = TextAlign.Center,
                            text = Res.string.game_name,

                            fontSize = 25.sp,
                            color = Color.White
                        )
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                MyText(
                    attribute = MyTextAttribute(
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.White,
                        text = Res.string.wait_for_players,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))

            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize() // Adjust weight as needed (0.8f for 5/7 weight)
                    .background(AppColors.mainColor)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(AppColors.secondColor)
                            .padding(10.dp),

                        ) {
                        IconButton(onClick = {
                            viewModel.logout()


                        }) {
                            Icon(
                                Icons.Default.ArrowBackIosNew,
                                contentDescription = "info",
                                tint = Color.White
                            )

                        }
                        Spacer(modifier = Modifier.width(25.dp))
                        MyText(
                            attribute = MyTextAttribute(
                                textAlign = TextAlign.Center,
                                text = Res.string.game_name,

                                fontSize = 25.sp,
                                color = Color.White
                            )
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    if (viewModel.timeStart != -1L) {
                        CountdownTimer(
                            viewModel,

                            )
                    }

                    Text(
                        text = if (viewModel.player == viewModel.turn) {
                            "Your Turn "
                        } else {
                            "Current Player ${viewModel.player}"
                        },
                        fontSize = 20.sp, color = Color.White,
                        fontFamily = FontFamilies.getMontserrat()
                    )
                    Spacer(modifier = Modifier.height(12.dp)) // Spacing between Text and Grid
                    LazyVerticalGrid(

                        columns = GridCells.Fixed(boardSize),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        for (i in viewModel.board.indices) {
                            item {

                                ElevatedButton(
                                    modifier = Modifier.padding(10.dp),
                                    enabled = if (state.value.loading) false else viewModel.player == viewModel.turn,
                                    shape = CircleShape,
                                    contentPadding = PaddingValues(horizontal = 10.dp, 10.dp),
                                    border = BorderStroke(
                                        1.dp,
                                        Color(0xffE3B65251).copy(alpha = .5f)
                                    ),
                                    elevation = ButtonDefaults.elevatedButtonElevation(
                                        defaultElevation = 2.dp
                                    ),
                                    colors = ButtonDefaults.elevatedButtonColors(
                                        containerColor =
                                        if (viewModel.board[i].contains("-1")) {
                                            Color(0xffE3B65251).copy(alpha = .7f)

                                        } else if (viewModel.number1 == i + 1 || viewModel.number2 == i + 1) {
                                            Color(0xffE3B65251)
                                        } else {
                                            Color(0xff17B65251)
                                        },
                                        disabledContainerColor =
                                        if (viewModel.board[i].contains("-1")) {
                                            Color(0xff17B65251).copy(alpha = .7f)
                                        } else {
                                            Color(0xff17B65251)
                                        },
                                        contentColor = Color.White,
                                        disabledContentColor = Color.White
                                    ),
                                    onClick = {
                                        if (viewModel.player == viewModel.turn) {
                                            if (!viewModel.board[i].contains("-1")) {
                                                if (viewModel.number1 != i + 1) {
                                                    viewModel.selectNumbers(i + 1)
                                                }
                                            }
                                        }
                                    }) {
                                    Text(
                                        if (viewModel.board[i].trim()
                                                .contains("-1")
                                        ) "X" else viewModel.board[i].trim(),
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontFamily = FontFamilies.getMontserrat()
                                    )
                                }
                            }
                        }

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    KBannerAd(
                        modifier = Modifier.width(350.dp).height(50.dp)
                            .background(Color.Transparent).align(Alignment.CenterHorizontally),
                        adBannerUnitId = AdServices.bannerId,
                        type = KAdmobBannerType.BANNER
                    )

                }

            }
        }
    }
}