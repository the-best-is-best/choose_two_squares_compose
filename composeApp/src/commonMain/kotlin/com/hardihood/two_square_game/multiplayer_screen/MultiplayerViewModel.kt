package com.hardihood.two_square_game.multiplayer_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.screenModelScope
import com.hardihood.two_square_game.core.BaseApiViewModel
import com.hardihood.two_square_game.core.main.domain.request.CreateOrJoinRoomRequest
import com.hardihood.two_square_game.core.main.domain.use_case.CreateOrJoinRoomUseCase
import com.hardihood.two_square_game.core.main.domain.use_case.DeleteRoomUseCase
import com.hardihood.two_square_game.core.main.domain.use_case.GetDataRoomUseCase
import com.hardihood.two_square_game.core.main.domain.use_case.LogoutRoomUseCase
import com.hardihood.two_square_game.core.main.domain.use_case.PlayGameUseCase
import com.hardihood.two_square_game.main.domain.request.LogoutRoomRequest
import com.hardihood.two_square_game.main.domain.request.PlayRoomRequest
import io.github.firebase_database.KFirebaseDatabase

//import io.github.firebase_database.KFirebaseDatabase
import io.github.handleerrorapi.Failure
import kotlinx.coroutines.launch

class MultiplayerViewModel(
    private val createOrJoinRoomUseCase: CreateOrJoinRoomUseCase,
    private val playGameUseCase: PlayGameUseCase,
    private val getDataRoomUseCase: GetDataRoomUseCase,
    private val logoutRoomUseCase: LogoutRoomUseCase,
    private val deleteRoomUseCase: DeleteRoomUseCase,
) : BaseApiViewModel() {
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


    var myDatabase = KFirebaseDatabase()

    private var currentDatabase: Map<*, *>? = null


    fun startGame(boardSize: Int, numberOfPlayer: Int) {

        this.numberOfPlayer = numberOfPlayer
        screenModelScope.launch {
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


            if (messageApi == "Join Room") {
                val roomData = mapOf("message" to "joined", "currentPlayer" to player)
//                myDatabase.child(idRoom!!.toString()).setValue(roomData)
                myDatabase.write(idRoom!!.toString(), roomData)
                playerJoined()

            } else {
                println("start delete room")
//                myDatabase.child(idRoom!!.toString()).removeValue()
                myDatabase.delete(idRoom!!.toString())
//                myDatabase.child(idRoom!!.toString()).setValue(mapOf("created" to 1))
                addListener()
            }
        }
    }

    private fun playerJoined() {
        timeStart = 30
        gameStarted = true
        addListener()

    }

    private fun addListener() {

        screenModelScope.launch {
            myDatabase.addObserveValueListener(idRoom!!.toString()).collect { res ->
                res.onSuccess {
                    val value = if (it is Map<*, *>) it else null
                    println("new data value $value")

                    if (currentDatabase != value) {
                        currentDatabase = value

                        if (value != null) {
                            try {
                                turn = value["nextTurn"].toString().toInt()

                            } catch (e: Exception) {
                            }

                            if (value["message"] == "joined") {
                                playerJoined()
                            } else if (value["message"] == "player win" || value["message"] == "Player Win") {
                                endGame(value["currentPlayer"].toString().toInt())
                            } else if (value["message"] == "player lost") {
                                lostPlayer(value["currentPlayer"].toString().toInt())
                            } else if (value["message"] == "No One Win The Game") {
                                endGame(0)
                            } else if (value["message"] == "Get Data Player") {

                                getBoard()


                            } else if (value["message"] == "Start Time") {
                                timeStart = 30
                                startTimer()
                            } else if (value["message"].toString() == "room issue") {
                                roomIssue()
                            } else {
                                println("firebase got nothing ${value["message"]}")
                            }

                        }

                    }
                }
                res.onFailure {
                    println("listen failed $idRoom")
                }
            }
        }
    }
    fun selectNumbers(number: Int) {
        if (number1 == null) {
            number1 = number
            return
        } else {
            number2 = number
        }

        played(number1!!, number2!!)

        number1 = null
        number2 = null
    }

    private fun played(number1: Int, number2: Int) {
        screenModelScope.launch {
            state.value = state.value.copy(
                loading = true
            )
            val result = playGameUseCase(
                playRoomRequest = PlayRoomRequest(
                    roomId = idRoom!!,
                    playerId = player, // This represents the player making the move
                    number1 = number1,
                    number2 = number2
                )
            )

            result.fold(
                right = {
                    when (it.message) {
                        "No One Win The Game" -> {
                            getBoardLocal(number1, number2)
                            timeStart = -1
                            val roomData =
                                mapOf("message" to "No One Win The Game", "currentPlayer" to turn)
                            updateRoom(roomData)
                            gameDraw = true
                        }

                        "Next Player" -> {
                            getBoardLocal(number1, number2)
                            // Move to the next player
                            if (turn == numberOfPlayer) {
                                turn = 1 // Reset to player 1 after the last player
                            } else {
                                turn++ // Move to the next player
                            }
                            timeStart = -1
                            val roomData = mapOf(
                                "message" to "Get Data Player",
                                "nextTurn" to turn,  // This should be the next player's turn
                                "currentPlayer" to player // This stays the current player (the one making the move)
                            )
                            updateRoom(roomData)
                        }

                        "Player Win" -> {
                            timeStart = -1
                            val roomData =
                                mapOf("message" to "Player Win", "currentPlayer" to player)
                            updateRoom(roomData)
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


    private fun startTimer() {
        timeStart = 30
        if (!gameStarted) {
            gameStarted = true
        }
    }


    private fun getBoardLocal(number1: Int, number2: Int) {
        board[number1 - 1] = "-1"
        board[number2 - 1] = "-1"
    }

    private fun getBoard() {
        timeStart = 15

        state.value = state.value.copy(
                loading = true
            )

            screenModelScope.launch {
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
                updateRoom(roomData)


                state.value = state.value.copy(
                    loading = false,

                    )
                return@launch
            }


    }

    fun timeOut() {
        screenModelScope.launch {
            state.value = state.value.copy(
                loading = true
            )
            deleteRoomUseCase.invoke(idRoom!!)
            endGame(-1)
        }
    }

    fun deleteRoom() {
        if (idRoom != null) {
            screenModelScope.launch {
                deleteRoomUseCase.invoke(idRoom!!)
            }

        }
    }

    fun logout() {
        state.value = state.value.copy(
            loading = true
        )
        screenModelScope.launch {
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

                    endGame = true

                } else {
                    deleteRoomUseCase.invoke(idRoom!!)
                    if (gameStarted && !roomError) {

                        val roomData = mapOf("message" to "player lost", "currentPlayer" to player)
                        updateRoom(roomData)

                        endGame = true


                    } else if (gameStarted && roomError) {
                        val roomData = mapOf("message" to "Room issue", "currentPlayer" to player)
                        updateRoom(roomData)


                    }
                }

                state.value = state.value.copy(
                    loading = false
                )

            } else {
                endGame = true
            }

        }
    }



    private fun endGame(player: Int) {
        playerWin = player

        endGame = true
        if (idRoom != null) {
            screenModelScope.launch {
                deleteRoomUseCase.invoke(idRoom!!)

            }
        }
    }

    private fun lostPlayer(player: Int) {
        playerLost = player
        endGame = true
        if (idRoom != null) {
            screenModelScope.launch {
                deleteRoomUseCase.invoke(idRoom!!)

            }
        }

    }

    private fun roomIssue() {
        playerWin = null
        endGame = true
        if (idRoom != null) {
            screenModelScope.launch {
                deleteRoomUseCase.invoke(idRoom!!)

            }
        }
    }


    private fun updateRoom(roomData: Map<String, Any>) {
        screenModelScope.launch {
//            myDatabase.child(idRoom!!.toString()).updateChildren(roomData)
            // }
            val res = myDatabase.update(idRoom!!.toString(), roomData)
            res.onSuccess {
                println("update room $idRoom")
            }
            res.onFailure {
                println("update room failed $idRoom")
            }
        }
    }

}

