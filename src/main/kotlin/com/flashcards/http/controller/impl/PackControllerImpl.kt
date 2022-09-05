package com.flashcards.http.controller

import com.flashcards.db.dao.impl.PackDAOImpl
import com.flashcards.model.Pack
import io.ktor.server.plugins.*
import kotlinx.coroutines.runBlocking

class PackControllerImpl : PackController {

    val packDAO = PackDAOImpl().apply {
        runBlocking {}
    }

    override suspend fun getPackById(id: Int?): Pack =
        id?.let { packDAO.getPackById(id) ?: throw BadRequestException("There is no a such card") }
            ?: throw BadRequestException("Provide pack id")
}