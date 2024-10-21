package com.hardihood.two_square_game.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import choose_two_squares.composeapp.generated.resources.Res
import choose_two_squares.composeapp.generated.resources.game_icon
import choose_two_squares.composeapp.generated.resources.game_name
import choose_two_squares.composeapp.generated.resources.how_many_bots
import choose_two_squares.composeapp.generated.resources.how_many_players
import choose_two_squares.composeapp.generated.resources.how_to_play
import choose_two_squares.composeapp.generated.resources.multiplayer_game
import choose_two_squares.composeapp.generated.resources.no
import choose_two_squares.composeapp.generated.resources.num1
import choose_two_squares.composeapp.generated.resources.num2
import choose_two_squares.composeapp.generated.resources.num3
import choose_two_squares.composeapp.generated.resources.num4
import choose_two_squares.composeapp.generated.resources.play_with_friends
import choose_two_squares.composeapp.generated.resources.privacy_policy
import choose_two_squares.composeapp.generated.resources.start_game
import choose_two_squares.composeapp.generated.resources.yes
import com.hardihood.two_square_game.components.AskQuestionDialog
import com.hardihood.two_square_game.components.MyText
import com.hardihood.two_square_game.components.MyTextAttribute
import com.hardihood.two_square_game.core.AppColors
import com.hardihood.two_square_game.core.FontFamilies
import com.hardihood.two_square_game.game_screen.GameScreen
import com.hardihood.two_square_game.home_screen.components.HomeAppBar
import com.hardihood.two_square_game.how_to_play_screen.HowToPlayScreen
import com.hardihood.two_square_game.multiplayer_screen.MultiplayerScreen
import com.hardihood.two_square_game.privacy_screen.PrivacyScreen
import io.github.compose_searchable_dropdown.SearchableDropDown
import io.github.compose_searchable_dropdown.states.rememberDropdownStates
import org.jetbrains.compose.resources.painterResource

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.koinNavigatorScreenModel<HomeViewModel>()

        uiController(viewModel, navigator)
        val selectedItemsState = rememberDropdownStates(value = viewModel.selectedTypeModeGame)

        // ui
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {

            LazyColumn {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HomeAppBar()
                        Spacer(modifier = Modifier.padding(top = 20.dp))
                        Image(
                            modifier = Modifier
                                .height(100.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White),
                            painter =
                            painterResource(Res.drawable.game_icon),
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.padding(top = 10.dp))

                        MyText(
                            attribute = MyTextAttribute(
                                textAlign = TextAlign.Center,
                                text = Res.string.game_name,

                                fontSize = 16.sp,
                                color = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.padding(top = 30.dp))

                        SearchableDropDown(
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White,

                                ),
                            listOfItems = viewModel.typesModeGame,
                            state = selectedItemsState,
                            dropdownItem = {
                                Text(
                                    modifier = Modifier.padding(10.dp),
                                    text = it.name,
                                    color = AppColors.secondColor,
                                    fontSize = 20.sp,
                                    fontFamily = FontFamilies.getMontserrat()
                                )
                            },
                            onDropDownItemSelected = {
                                viewModel.changeMode(it!!)

                            },
                            selectedOptionTextDisplay = { it.name }

                        )

                        Spacer(modifier = Modifier.padding(10.dp))

                        ElevatedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 45.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = AppColors.secondColor
                            ),
                            contentPadding = PaddingValues(),
                            onClick = {
                                viewModel.playOffline()


                            }) {
                            MyText(
                                attribute = MyTextAttribute(
                                    text = Res.string.start_game,
                                    fontSize = 25.sp,
                                    color = Color.White
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        ElevatedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 45.dp),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(),

                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = AppColors.secondColor
                            ),
                            onClick = {
                                viewModel.playOnline()
                            }) {
                            MyText(
                                attribute = MyTextAttribute(
                                    textAlign = TextAlign.Center,
                                    text = Res.string.multiplayer_game,
                                    fontSize = 25.sp,
                                    color = Color.White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        ElevatedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 45.dp),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(),

                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = AppColors.secondColor
                            ),
                            onClick = {
                                navigator.push(HowToPlayScreen())
                            }) {
                            MyText(
                                attribute = MyTextAttribute(
                                    textAlign = TextAlign.Center,
                                    text = Res.string.how_to_play,
                                    fontSize = 25.sp,
                                    color = Color.White
                                )
                            )
                        }

                    }

                }


            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.End

            ) {
                ElevatedButton(
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 5.dp,
                        pressedElevation = 6.dp,
                        hoveredElevation = 4.dp
                    ),
                    //  shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = AppColors.secondColor
                    ),
                    onClick = {
                        navigator.push(PrivacyScreen())
                    }) {
                    MyText(
                        attribute = MyTextAttribute(
                            textAlign = TextAlign.Center,
                            text = Res.string.privacy_policy,
                            fontSize = 15.sp,

                            color = Color.White
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

    }

    @Composable
    private fun uiController(viewModel: HomeViewModel, navigator: Navigator) {


        if (viewModel.showNumOfPlayerQuestion) {
            if (!viewModel.playOnline) {
                AskQuestionDialog(
                    question = if (viewModel.playWithFired) {
                        Res.string.how_many_players
                    } else {
                        Res.string.how_many_bots
                    },
                    btn1Click = {
                        viewModel.gameReady()
                        navigator.replaceAll(
                            GameScreen(
                                2,
                                viewModel.playWithFired,
                                viewModel.boardSize
                            )
                        )

                    },
                    btn2Click = {
                        viewModel.gameReady()
                        navigator.replaceAll(
                            GameScreen(
                                3,
                                viewModel.playWithFired,
                                viewModel.boardSize
                            )
                        )

                    },
                    btn3Click = if (viewModel.selectedTypeModeGame.id != 3) {
                        null
                    } else {
                        {
                            viewModel.gameReady()
                            navigator.replaceAll(
                                GameScreen(
                                    4,
                                    viewModel.playWithFired,
                                    viewModel.boardSize
                                )
                            )

                        }
                    },
                    btn1Text = if (viewModel.playWithFired) {
                        Res.string.num2
                    } else Res.string.num1,
                    btn2Text = if (viewModel.playWithFired) {
                        Res.string.num3
                    } else {
                        Res.string.num2
                    },
                    btn3Text = if (viewModel.playWithFired) {
                        Res.string.num4
                    } else {
                        Res.string.num3
                    },
                    onDismissRequest = {
                        viewModel.showNumOfPlayerQuestion = false
                    }
                )
            } else {
                if (viewModel.selectedTypeModeGame.id == 1) {
                    viewModel.gameReady()
                    navigator.replaceAll(MultiplayerScreen(viewModel.boardSize, 2))

                } else {
                    AskQuestionDialog(
                        question =
                        Res.string.how_many_players,
                        btn1Click = {
                            viewModel.gameReady()
                            navigator.replaceAll(MultiplayerScreen(viewModel.boardSize, 2))

                        },
                        btn2Click = {
                            viewModel.gameReady()
                            navigator.replaceAll(MultiplayerScreen(viewModel.boardSize, 3))

                        },
                        btn3Click = if (viewModel.selectedTypeModeGame.id != 3) {
                            null
                        } else {
                            {
                                viewModel.gameReady()

                                navigator.replaceAll(MultiplayerScreen(viewModel.boardSize, 4))

                            }
                        },
                        btn1Text = Res.string.num2,
                        btn2Text = Res.string.num3,
                        btn3Text = Res.string.num4,
                        onDismissRequest = {
                            viewModel.showNumOfPlayerQuestion = false
                        }
                    )
                }
            }
        }

        if (viewModel.showPlayWithFriendsQuestion) {
            AskQuestionDialog(
                question = Res.string.play_with_friends,

                btn1Text = Res.string.yes,
                btn2Text = Res.string.no,
                btn1Click = {
                    viewModel.showPlayWithFriendsQuestion = false
                    playWithFriend(navigator, viewModel, true)

                },
                btn2Click = {
                    viewModel.showPlayWithFriendsQuestion = false
                    playWithFriend(navigator, viewModel, false)

                },
                onDismissRequest = {
                    viewModel.showPlayWithFriendsQuestion = false
                },

                )
        }


    }

    private fun playWithFriend(navigator: Navigator, viewModel: HomeViewModel, b: Boolean) {
        if (viewModel.selectedTypeModeGame.id == 1) {
            navigator.replaceAll(GameScreen(2, b, viewModel.boardSize))

        } else {
            viewModel.playWithFired = b
            viewModel.showNumOfPlayerQuestion = true
        }
    }


}