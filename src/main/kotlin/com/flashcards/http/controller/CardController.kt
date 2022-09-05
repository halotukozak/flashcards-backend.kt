package com.flashcards.http.controller

class ContentControllerImpl:ContentController{
    override val getCardById: Return type for property getCardById cannot be resolved
}

interface ContentController{
    val getCardById(id: Int) : Card
}