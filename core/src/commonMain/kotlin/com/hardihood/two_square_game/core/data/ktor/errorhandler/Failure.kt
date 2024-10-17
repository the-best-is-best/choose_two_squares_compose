package io.github.handleerrorapi

data class Failure(
    val statusCode: Int? = null,
    val messages: String? = null,
)