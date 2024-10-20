package com.hardihood.two_square_game.game_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow
import kotlin.properties.Delegates
import kotlin.random.Random

class GameViewModel : ScreenModel {
    var playWithFriends = false

    var board: MutableList<String> = mutableListOf()
    var player by mutableIntStateOf(1)
    var yourTurn by mutableIntStateOf(1)
    var cannotPlayHere by mutableStateOf(false)
    var gameEnd by mutableStateOf(false)
    var gameDraw by mutableStateOf(false)

    private var numberOfPlayer: Int = 2

    var number1 by mutableStateOf<Int?>(null)
    var number2 by mutableStateOf<Int?>(null)

    var boardSize: Int = 0
    private var totalGameNum: Int = 0

    private var availableGame: MutableList<MutableList<Int>> = mutableListOf()

    private var _yourScore by Delegates.notNull<Double>()


    fun selectNumber(number: Int) {
        if (number1 == null) {
            number1 = number
            return
        } else {
            number2 = number
        }

        _action(number1!!, number2!!)

        number1 = null
        number2 = null

    }

    fun startGame(numOfPlayer: Int, withFriend: Boolean, sendBoard: Int) {
        playWithFriends = withFriend
        // adLoaded = true
        boardSize = sendBoard
        totalGameNum = boardSize.toDouble().pow(2.0).toInt()
        numberOfPlayer = numOfPlayer

        for (i in 0 until totalGameNum) {
            board.add("${i + 1}")
        }

        if (!playWithFriends) {
            _yourScore =
                ((boardSize * numOfPlayer) / (boardSize + numOfPlayer)) * (10.0 * numOfPlayer / 2.0).pow(
                    numOfPlayer.toDouble()
                )
            if (boardSize == 4) {
                _yourScore *= 2
            }
            yourTurn = Random.nextInt(numOfPlayer) + 1
            if (yourTurn != 1) {
                _action(null, null) // Replace with your actual action logic
            }
        }

    }

    private fun _action(action1: Int? = null, action2: Int? = null) {

        if (action1 == null && action2 == null) {

            availableGame = MutableList(totalGameNum) { MutableList(4) { 0 } }
            for (i in board.indices) {
                // Check if the current position is on the edge of the board
                if ((i + 1) % boardSize != 0) {
                    if ((i + 1) < totalGameNum) {
                        availableGame[i][0] = i + 2
                    }
                }

                if ((i) % (boardSize) != 0) {
                    if ((i - 1) >= 0) {
                        availableGame[i][1] = i
                    }
                }

                if ((i - boardSize) >= 0) {
                    availableGame[i][2] = i + 1 - boardSize
                }

                if ((i + boardSize) < totalGameNum) {
                    availableGame[i][3] = i + 1 + boardSize
                }
            }

            _aiPlay()


        } else {
            if (board[action1!! - 1] != "x" && board[action2!! - 1] != "x") {
                if (abs(action2 - action1) == boardSize ||
                    abs(action2 - action1) == 1
                ) {
                    if (action1 != totalGameNum || action2 != totalGameNum) {
                        if (action1 % boardSize == 0 && action2 == action1 + 1 ||
                            action2 % boardSize == 0 && action1 == action2 + 1
                        ) {
                            cannotPlayHere = true

                            return
                        }
                    }


                    board[action1 - 1] = "x"
                    board[action2 - 1] = "x"
                    availableGame =
                        MutableList(totalGameNum) { MutableList(4) { 0 } }
                    var turns = false
                    var draw = true

                    for (i in board.indices) {
                        if (board[i] != "x") {
                            draw = false

                            if ((i + 1) % boardSize != 0) {
                                if ((i + 1) < totalGameNum && board[i + 1] != "x") {
                                    turns = true
                                    availableGame[i][0] = i + 2
                                }
                            }

                            if ((i) % (boardSize) != 0) {
                                if ((i - 1) >= 0 && board[i - 1] != "x") {
                                    turns = true
                                    availableGame[i][1] = i
                                }
                            }

                            if ((i - boardSize) >= 0 && board[i - boardSize] != "x") {
                                turns = true
                                availableGame[i][2] = i + 1 - boardSize
                            }

                            if ((i + boardSize) < totalGameNum &&
                                board[i + boardSize] != "x"
                            ) {
                                turns = true
                                availableGame[i][3] = i + 1 + boardSize
                            }
                        }
                    }
                    if (draw) {
                        gameDraw = true

                        return
                    }

                    if (turns) {
                        if (player == numberOfPlayer) {
                            player = 1
                        } else {
                            player++
                        }
                        if (!playWithFriends) {
                            if (player != yourTurn) {
                                _aiPlay()
                            }
                        }
                        cannotPlayHere = false
                    } else {
                        gameEnd = true
                    }
                } else {
                    cannotPlayHere = true
                }
            } else {
                cannotPlayHere = true
            }
        }
    }

    private fun _aiPlay(isFirstMove: Boolean = true) {
        val waitTime = if (isFirstMove) Random.nextInt(1000) + 1000 else 0

        screenModelScope.launch {
            delay(waitTime.toLong())
            var played = false
            for (x in 0 until totalGameNum) {
                var turns = 0
                var numY = 0

                for (y in 0..3) {
                    if (availableGame[x][y] != 0) {
                        turns++
                        numY = availableGame[x][y]
                    }
                }

                val avalibleGameAccess: MutableList<Int> = mutableListOf()

                if (turns == 1) {
                    var num2 = 0

                    for (z in 0..3) {
                        if (availableGame[numY - 1][z] != 0 &&
                            availableGame[numY - 1][z] != x + 1
                        ) {
                            num2 = availableGame[numY - 1][z]
                            avalibleGameAccess.add(num2)
                        }
                    }
                    if (avalibleGameAccess.count() == 1) {
                        _action(numY, num2)
                        played = true
                        break
                    } else if (avalibleGameAccess.isNotEmpty()) {
                        val randomV: Int = Random
                            .nextInt(avalibleGameAccess.count() * avalibleGameAccess.count())
                        num2 =
                            avalibleGameAccess[(floor((randomV / avalibleGameAccess.count()).toDouble()).toInt())]

                        _action(numY, num2)
                        played = true
                        break
                    }
                }
            }
            if (!played) {
                val index: Int = Random.nextInt(totalGameNum)
                val index2: Int = Random.nextInt(4)

                if (availableGame[index][index2] != 0) {
                    _action(index + 1, availableGame[index][index2])
                } else {
                    _aiPlay(false)
                    return@launch
                }
            }


        }

    }


}