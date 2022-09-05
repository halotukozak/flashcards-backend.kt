package com.flashcards.http.routing

import com.flashcards.model.User
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cardsRoutes() {
    route("/fishcards") {
        get("hello") {
            call.respondText("Hello ${call.principal<User>()?.username}")
        }
    }
}