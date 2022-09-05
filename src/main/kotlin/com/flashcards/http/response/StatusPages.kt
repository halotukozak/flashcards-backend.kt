package com.flashcards.plugins

import com.flashcards.http.response.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.reasons.joinToString())
        }

        status(HttpStatusCode.InternalServerError) { call, statusCode ->
            call.respond(statusCode, Response.ErrorResponse(statusCode, statusCode.description))
        }
        status(HttpStatusCode.Unauthorized) { call, statusCode ->
            call.respond(statusCode, Response.ErrorResponse(statusCode, statusCode.description))
        }
        status(HttpStatusCode.MethodNotAllowed) { call, statusCode ->
            call.respond(statusCode, Response.ErrorResponse(statusCode, statusCode.description))
        }
    }
}