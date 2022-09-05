package com.flashcards.http.routing

import com.flashcards.model.User
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes() {
    route("/me") {
        get {
            val user = call.user
            call.respond(user)
        }
    }

}
val ApplicationCall.user get() = authentication.principal<User>()!!
