package com.hardihood.two_square_game.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import io.github.handleerrorapi.Failure
import io.github.tbib.compose_toast.toast_ui.EnumToastType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

open class BaseApiViewModel : ScreenModel {

    private val parentJob = Job()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        parentJob.cancel()
        // Handle the error
    }
    private val viewModelScope =
        CoroutineScope(Dispatchers.Main + parentJob + coroutineExceptionHandler)

    override fun onDispose() {
        super.onDispose()
        parentJob.cancel()

    }

    var showDialog by mutableStateOf(false)

    private val _state = MutableStateFlow(State()) // State holder
    var state: MutableStateFlow<State> = _state

    private val _toastMessageError = MutableSharedFlow<Failure>()
    val toastMessageError: SharedFlow<Failure> = _toastMessageError

    data class State(
        val loading: Boolean = false,
        val errorMessage: Failure? = null,
        val formValid: Boolean = false,
        val toastType: EnumToastType = EnumToastType.ERROR,
        val isSuccess: Boolean = false,
        val isRefresh: Boolean = false,

        )

    fun handleError() {

        if (state.value.errorMessage != null) {
            if (state.value.errorMessage!!.statusCode != null && ((state.value.errorMessage!!.statusCode == 1) || state.value.errorMessage!!.statusCode!! < 500)) {
                showDialog = true
            } else {

                sendMessageError(state.value.errorMessage!!)
            }
        }
    }

    private fun sendMessageError(message: Failure) {
        viewModelScope.launch {
            state.value = state.value.copy(
                errorMessage = message,
                loading = false,
                isSuccess = false,
                toastType = EnumToastType.ERROR
            )
            _toastMessageError.emit(message)
        }
    }


    fun showErrorState() {
        viewModelScope.launch {
            if (state.value.errorMessage != null) {
                if (state.value.errorMessage!!.statusCode != null && ((state.value.errorMessage!!.statusCode == 1) || state.value.errorMessage!!.statusCode!! < 500)) {
                    showDialog = true
                } else {

                    sendMessageError(state.value.errorMessage!!)
                }
            }
        }
    }

}