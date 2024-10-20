package com.hardihood.two_square_game.core.main.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable()
internal abstract class BaseResponse<T> {
    abstract val success: Boolean?

    @SerialName("messages")
    abstract val messages: List<String>?

    @SerialName("data")
    abstract val `data`: T?


}
