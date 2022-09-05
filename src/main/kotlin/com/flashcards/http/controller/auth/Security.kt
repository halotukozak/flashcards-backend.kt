package com.flashcards.plugins

import com.flashcards.db.dao.impl.AuthDaoImpl
import com.flashcards.http.controller.JWTController
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*


fun Application.configureSecurity(
    jwt: JWTController,
) {
    val authDAO = AuthDaoImpl()
    install(Authentication) {
        jwt("jwt") {
            verifier(jwt.verifier)
            realm = "ktor.io"
            validate {
                it.payload.getClaim("id").asString()?.let { userId ->
                    authDAO.findUserById(userId)
                }
            }
        }
    }
}
