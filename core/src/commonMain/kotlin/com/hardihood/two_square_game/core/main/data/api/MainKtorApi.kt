package com.hardihood.two_square_game.core.main.data.api

import choose_two_squares.core.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal abstract class MainKtorApi {

    val client = HttpClient {
        expectSuccess = true
        defaultRequest {
            header("Content-Type", "application/json")
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    prettyPrint = true // Optional for readability
                }
            )
        }

    }


    fun HttpRequestBuilder.pathUrl(path: String) {
        url {
            takeFrom(BuildConfig.DOMAIN)
                .path(BuildConfig.PATH, path)
        }
    }
}