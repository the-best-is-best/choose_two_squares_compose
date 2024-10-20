package com.hardihood.two_square_game.multiplayer_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.hardihood.two_square_game.core.main.domain.use_case.CreateOrJoinRoomUseCase
import com.hardihood.two_square_game.core.main.domain.use_case.DeleteRoomUseCase
import com.hardihood.two_square_game.core.main.domain.use_case.GetDataRoomUseCase
import com.hardihood.two_square_game.core.main.domain.use_case.LogoutRoomUseCase
import com.hardihood.two_square_game.core.main.domain.use_case.PlayGameUseCase
import com.hardihood.two_square_game.main.domain.request.LogoutRoomRequest
import com.hardihood.two_square_game.main.domain.request.PlayRoomRequest
import io.github.handleerrorapi.Failure
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MultiplayerViewModel(
    private val createOrJoinRoomUseCase: CreateOrJoinRoomUseCase,
    private val playGameUseCase: PlayGameUseCase,
    private val getDataRoomUseCase: GetDataRoomUseCase,
    private val logoutRoomUseCase: LogoutRoomUseCase,
    private val deleteRoomUseCase: DeleteRoomUseCase,
) : ScreenModel {
    private var numberOfPlayer: Int = 2


    var board: MutableList<String> = mutableListOf()
    var player by mutableIntStateOf(1)
    var playerLost by mutableStateOf<Int?>(null)
    var turn by mutableIntStateOf(1)
    var idRoom by mutableStateOf<Int?>(null)

    var playerWin by mutableStateOf<Int?>(null)
    var number1 by mutableStateOf<Int?>(null)
    var number2 by mutableStateOf<Int?>(null)
    var gameStarted by mutableStateOf(false)
    var gameDraw by mutableStateOf(false)
    var endGame by mutableStateOf(false)

    var roomError by mutableStateOf(false)

    var timeStart by mutableLongStateOf(30L)


    var myDatabase = RealTimeDatabase()

    //   var canBack by mutableStateOf(false)

    fun resetGame() {
        turn = 1
        this.board.clear()
        this.numberOfPlayer = 2
        this.endGame = false
        this.roomError = false
        this.playerLost = null
        this.playerWin = null
        this.number1 = null
        this.number2 = null
        this.gameStarted = false
        this.gameDraw = false
        this.timeStart = 30
        this.player = 1
        this.idRoom = null
        this.myDatabase = RealTimeDatabase()

    }

    fun startGame(boardSize: Int, numberOfPlayer: Int) {

        this.numberOfPlayer = numberOfPlayer
        GlobalScope.launch {
            state.value = state.value.copy(
                loading = true
            )
            val result = createOrJoinRoomUseCase(
                createOrJoinRoomRequest = CreateOrJoinRoomRequest(
                    boardSize = boardSize,
                    numOfPlayer = numberOfPlayer
                )
            )
            var messageApi: String? = null
            result.fold(
                right = {
                    state.value = state.value.copy(
                        loading = false
                    )
                    idRoom = it.id
                    player = it.player!!
                    board = it.board!!.toMutableList().map { it.toString() }.toMutableList()
                    messageApi = it.message
                },
                left = {
                    state.value = state.value.copy(
                        loading = false,
                        errorMessage = it,
                    )
                    handleError()
                    return@launch
                }
            )
            myDatabase.roomConnect(idRoom!!)

            if (messageApi == "Join Room") {
                val roomData = mapOf("message" to "joined", "currentPlayer" to player)
                myDatabase.myRef!!.setValue(roomData)
                playerJoined()
            }
        }
    }

    fun playerJoined() {
        timeStart = 30
        gameStarted = true
    }

    fun selectNumbers(number: Int) {
        if (number1 == null) {
            number1 = number
            return
        } else {
            number2 = number
        }

        _played(number1!!, number2!!)

        number1 = null
        number2 = null
    }

    private fun _played(number1: Int, number2: Int) {

        GlobalScope.launch {
            state.value = state.value.copy(
                loading = true
            )
            val result = playGameUseCase(
                playRoomRequest = PlayRoomRequest(
                    roomId = idRoom!!,
                    playerId = player,
                    number1 = number1,
                    number2 = number2
                )
            )

            result.fold(
                right = {

                    when (it.message) {
                        "No One Win The Game" -> {
                            _getBoardLocal(number1, number2)
                            timeStart = -1
                            val roomData =
                                mapOf("message" to "No One Win The Game", "currentPlayer" to player)
                            myDatabase.myRef!!.setValue(roomData)

                            gameDraw = true
                        }

                        "Next Player" -> {
                            _getBoardLocal(number1, number2)
                            var nextTurn: Int = player
                            if (nextTurn == numberOfPlayer) {
                                nextTurn = 1
                            } else {
                                nextTurn++
                            }

                            timeStart = -1
                            val roomData = mapOf(
                                "message" to "Get Data Player",
                                "nextTurn" to nextTurn,
                                "currentPlayer" to player
                            )
                            myDatabase.myRef!!.setValue(roomData)

                        }

                        "Player Win" -> {
                            timeStart = -1

                            val roomData =
                                mapOf("message" to "Player Win", "currentPlayer" to player)
                            myDatabase.myRef!!.setValue(roomData)


                            //val sendData = mapOf("message" to "Player Win-$player")

                        }

                        "You can't play here" -> {
                            state.value = state.value.copy(
                                loading = false,
                                errorMessage = Failure(null, it.message),
                            )
                            handleError()
                        }

                        else -> {
                            roomError = true
                            logout()
                        }
                    }
                },
                left = {

                    state.value = state.value.copy(
                        loading = false,
                        errorMessage = it,
                    )
                    handleError()
                    return@launch
                }

            )
            state.value = state.value.copy(
                loading = false
            )

        }
    }

    fun startTimer() {
        timeStart = 30
        if (!gameStarted) {
            gameStarted = true
        }
    }


    private fun _getBoardLocal(number1: Int, number2: Int) {
        board[number1 - 1] = "-1"
        board[number2 - 1] = "-1"
    }

    fun getBoard(playerTurn: Int) {
        timeStart = 15
        if (playerTurn == player) {
            state.value = state.value.copy(
                loading = true
            )

            GlobalScope.launch {
                val result = getDataRoomUseCase.invoke(
                    idRoom!!
                )

                result.fold(
                    right = {

                        board = it.board!!.toMutableList().map { it.toString() }.toMutableList()

                    },
                    left = {
                        state.value = state.value.copy(
                            loading = false,
                            errorMessage = it,
                        )
                        handleError()
                        return@launch
                    }
                )
                val roomData = mapOf("message" to "Start Time", "currentPlayer" to player)
                myDatabase.myRef!!.setValue(roomData)


                if (turn == numberOfPlayer) {
                    turn = 1
                } else {
                    turn += 1
                }
                state.value = state.value.copy(
                    loading = false,

                    )
                return@launch
            }

        } else {
            timeStart = -1
            if (turn == numberOfPlayer) {
                turn = 1
            } else {
                turn += 1
            }
        }


    }

    fun timeOut() {
        GlobalScope.launch {
            state.value = state.value.copy(
                loading = true
            )
            deleteRoomUseCase.invoke(idRoom!!)
            endGame(-1)
        }
    }

    fun deleteRoom() {
        GlobalScope.launch {
            deleteRoomUseCase.invoke(idRoom!!)
        }
        myDatabase.roomDisconnect(idRoom)
    }

    fun logout() {
        state.value = state.value.copy(
            loading = true
        )
        GlobalScope.launch {
            if (idRoom != null) {

                if (!gameStarted) {

                    try {
                        logoutRoomUseCase.invoke(
                            LogoutRoomRequest(
                                roomId = idRoom!!,
                                userId = player
                            )
                        )
                    } catch (_: Exception) {
                    }
                    myDatabase.roomDisconnect(idRoom)
                    endGame = true

                } else {
                    deleteRoomUseCase.invoke(idRoom!!)
                    if (gameStarted && !roomError) {

                        val roomData = mapOf("message" to "player lost", "currentPlayer" to player)
                        myDatabase.myRef?.setValue(roomData)
                        myDatabase.roomDisconnect(idRoom)
                        endGame = true


                    } else if (gameStarted && roomError) {
                        val roomData = mapOf("message" to "Room issue", "currentPlayer" to player)
                        myDatabase.myRef?.setValue(roomData)
                        myDatabase.roomDisconnect(idRoom)


                    }
                }

                state.value = state.value.copy(
                    loading = false
                )

            }

        }
    }

    fun endGame(player: Int) {
        playerWin = player

        endGame = true
        if (idRoom != null) {
            GlobalScope.launch {
                deleteRoomUseCase.invoke(idRoom!!)

            }
        }
    }

    fun lostPlayer(player: Int) {
        playerLost = player
        endGame = true
        if (idRoom != null) {
            GlobalScope.launch {
                deleteRoomUseCase.invoke(idRoom!!)

            }
        }

    }

    fun roomIssue() {
        playerWin = null
        endGame = true
        if (idRoom != null) {
            GlobalScope.launch {
                deleteRoomUseCase.invoke(idRoom!!)

            }
        }
    }
}