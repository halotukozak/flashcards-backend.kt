package com.flashcards.model

import kotlinx.serialization.Serializable

@Serializable
data class Pack(
    val id: Int?,
    var name: String,
    var cards: List<Card> = listOf(),
)