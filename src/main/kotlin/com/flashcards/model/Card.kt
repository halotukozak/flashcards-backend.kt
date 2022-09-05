package com.flashcards.model

import com.flashcards.db.CardsTable
import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val id: Int?,
    var pl: String,
    var en: String,
)