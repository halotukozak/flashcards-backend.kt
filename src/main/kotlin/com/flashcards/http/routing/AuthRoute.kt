package com.flashcards.http.routing

import com.flashcards.http.controller.auth.AuthController
import com.flashcards.http.request.RefreshTokenRequest
import com.flashcards.http.request.auth.LoginRequest
import com.flashcards.http.request.auth.RegisterRequest
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authenticationRoutes(authController: AuthController) {

    route("/auth") {
        post("/register") {
            val credentials = call.receive<RegisterRequest>()
            val registerResponse = authController.createUser(credentials)
            call.respond(registerResponse)
        }
        post("/login") {
            val credentials = call.receive<LoginRequest>()
            val loginTokenResponse = authController.authenticate(credentials)
            call.respond(loginTokenResponse)
        }

        post("/token") {
            val credentials = call.receive<RefreshTokenRequest>()
            val credentialsResponse = authController.refreshToken(credentials)
            call.respond(credentialsResponse)
        }

    }
}