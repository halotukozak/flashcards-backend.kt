package com.flashcards.http.controller

import com.flashcards.db.dao.impl.CardDAOImpl
import com.flashcards.model.Card
import io.ktor.server.plugins.*
import kotlinx.coroutines.runBlocking

class CardControllerImpl : CardController {

    private val cardDAO = CardDAOImpl().apply {
        runBlocking {}
    }
    override suspend fun getCardById(id: Int?): Card =
        id?.let { cardDAO.getCardById(id) ?: throw BadRequestException("There is no a such card") }
            ?: throw BadRequestException("Provide valid card ID")
}