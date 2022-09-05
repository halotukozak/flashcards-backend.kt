package com.flashcards.http.routing

import io.ktor.server.routing.*

fun Route.packsRoutes() {
//    route("/packs") {
//        get {
//            try {
//                val user = getCurrentUser()
//                val packs = dao.getUserPacks(user.id)
//                val loginResponse = contentController.login(
//                    email = credentials.email, password = credentials.password
//                )
//                val result = generateHttpResponse(loginResponse)
//                call.respond(result.code, result.body)
//                call.respond(HttpStatusCode.OK, packs)
//            } catch (e: Exception) {
//                call.respond(BadRequestException(e.message!!))
//            }
//        }
//        get("{id}") {
//            try {
//                val user = getCurrentUser()
//                val pack = dao.getUserPack(user.id, call.parameters["id"]?.toIntOrNull())
//                pack?.let { call.respond(HttpStatusCode.OK, pack) }
//            } catch ()
//        }
//    }
}