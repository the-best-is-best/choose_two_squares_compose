package com.hardihood.two_square_game.game_screen

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import choose_two_squares.composeapp.generated.resources.Res
import choose_two_squares.composeapp.generated.resources.game_draw
import choose_two_squares.composeapp.generated.resources.game_name
import choose_two_squares.composeapp.generated.resources.lose_game
import choose_two_squares.composeapp.generated.resources.player
import choose_two_squares.composeapp.generated.resources.win_game
import choose_two_squares.composeapp.generated.resources.you_cannot_play_here
import com.hardihood.two_square_game.components.MyText
import com.hardihood.two_square_game.components.MyTextAttribute
import com.hardihood.two_square_game.components.ShowDialog
import com.hardihood.two_square_game.core.AppColors
import com.hardihood.two_square_game.core.FontFamilies
import com.hardihood.two_square_game.core.services.AdServices
import com.hardihood.two_square_game.home_screen.HomeScreen
import io.github.kadmob.model.KAdmobBannerType
import io.github.kadmob.views.KBannerAd
import io.github.tbib.compose_toast.AdvToast
import io.github.tbib.compose_toast.rememberAdvToastStates
import io.github.tbib.compose_toast.toast_ui.EnumToastType
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

data class GameScreen(val numOfPlayer: Int, val withFriend: Boolean, val sendBoard: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val gameViewModel = koinScreenModel<GameViewModel>()

        var gameStart by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(!gameStart) {
            if (!gameStart) {
                gameStart = true
                gameViewModel.startGame(numOfPlayer, withFriend, sendBoard)
            }
        }
        val stateErrorToast = rememberAdvToastStates()

        val coroutineScope = rememberCoroutineScope()


        if (gameViewModel.cannotPlayHere) {
            val errorMessage = stringResource(Res.string.you_cannot_play_here)
            gameViewModel.cannotPlayHere = false
            coroutineScope.launch {
                stateErrorToast.show(
                    errorMessage
                )
            }

        }
        if (gameViewModel.gameDraw) {
            AdServices.showInterstitialAd()
            val message = stringResource(Res.string.game_draw)
            ShowDialog(message = message, onClick = {
                navigator.replaceAll(HomeScreen())
            })

        }

        if (gameViewModel.gameEnd) {
            AdServices.showInterstitialAd()
            val playerString = stringResource(Res.string.player)
            val winTheGameString = stringResource(Res.string.win_game)
            ShowDialog(message =
            if (gameViewModel.playWithFriends) {
                playerString + " " + gameViewModel.player + " " + winTheGameString

            } else {
                if (gameViewModel.player == gameViewModel.yourTurn) {
                    stringResource(Res.string.win_game)
                } else {
                    stringResource(Res.string.lose_game) + " " + gameViewModel.player
                }
            },

                onClick = {
                    navigator.replaceAll(HomeScreen())
                })

        }
        if (gameViewModel.boardSize != 0) {
            Box(
                modifier = Modifier
                    .fillMaxSize() // Adjust weight as needed (0.8f for 5/7 weight)
                    .background(AppColors.mainColor)
            ) {
                AdvToast.MakeToast(
                    state = stateErrorToast,
                    toastType = EnumToastType.ERROR,
                    paddingBottom = 50
                )

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
                        verticalAlignment = Alignment.CenterVertically


                    ) {
                        IconButton(onClick = {
                            AdServices.showInterstitialAd()
                            navigator.replaceAll(HomeScreen())
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

                    Text(
                        text = if (gameViewModel.playWithFriends) {
                            "Current Player ${gameViewModel.player}"
                        } else if (gameViewModel.player == gameViewModel.yourTurn) {
                            "Your Turn "
                        } else {
                            "Current Bot ${gameViewModel.player}"
                        },
                        fontSize = 20.sp, color = Color.White,
                        fontFamily = FontFamilies.getMontserrat()
                    )
                    Spacer(modifier = Modifier.height(12.dp)) // Spacing between Text and Grid
                    LazyVerticalGrid(

                        columns = GridCells.Fixed(gameViewModel.boardSize),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        for (i in gameViewModel.board.indices) {
                            item {

                                ElevatedButton(
                                    modifier = Modifier.padding(10.dp),
                                    enabled = if (gameViewModel.playWithFriends) {
                                        true
                                    } else {
                                        gameViewModel.player == gameViewModel.yourTurn
                                    },
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
                                        if (gameViewModel.board[i] == "x") {
                                            Color(0xffE3B65251).copy(alpha = .7f)

                                        } else if (gameViewModel.number1 == i + 1 || gameViewModel.number2 == i + 1) {
                                            Color(0xffE3B65251)
                                        } else {
                                            Color(0xff17B65251)
                                        },
                                        disabledContainerColor =
                                        if (gameViewModel.board[i] == "x") {
                                            Color(0xff17B65251).copy(alpha = .7f)
                                        } else {
                                            Color(0xff17B65251)
                                        },
                                        contentColor = Color.White,
                                        disabledContentColor = Color.White
                                    ),
                                    onClick = {
                                        if (gameViewModel.board[i] != "x") {
                                            if (gameViewModel.playWithFriends) {
                                                if (gameViewModel.number1 != i + 1) {
                                                    gameViewModel.selectNumber(i + 1)
                                                }
                                            } else if (gameViewModel.player == gameViewModel.yourTurn) {
                                                if (gameViewModel.number1 != i + 1) {
                                                    gameViewModel.selectNumber(i + 1)
                                                }
                                            }
                                        }
                                    }) {
                                    Text(
                                        gameViewModel.board[i].trim(),
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