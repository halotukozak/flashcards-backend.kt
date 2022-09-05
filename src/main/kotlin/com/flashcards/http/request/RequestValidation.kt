package com.flashcards.plugins

import com.flashcards.http.request.LoginRequest
import com.flashcards.http.request.RegisterRequest
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation() {
    install(RequestValidation) {

        validate<LoginRequest> { loginRequest ->
            val message = when {
                (loginRequest.email.isBlank()) -> "Email should not be blank"
                (loginRequest.password.isBlank()) -> "Password should not be blank"
                (!loginRequest.email.contains('@')) -> "Provide valid email"
                else -> return@validate ValidationResult.Valid
            }
            return@validate ValidationResult.Invalid(message)
        }

        validate<RegisterRequest> { registerRequest ->
            val message = when {
                (registerRequest.email.isBlank()) -> "Email should not be blank"
                (registerRequest.password.isBlank()) -> "Password should not be blank"
                (registerRequest.username.isBlank()) -> "Username should not be blank"
                (!registerRequest.email.contains('@')) -> "Provide valid email"
                (registerRequest.username.length !in (4..30)) -> "Username should be of min 4 and max 30 character in length"
                (registerRequest.password.length !in (8..50)) -> "Password should be of min 8 and max 50 character in length"
                else -> return@validate ValidationResult.Valid
            }
            return@validate ValidationResult.Invalid(message)
        }
    }
}