package com.flashcards.http.controller.auth

import com.flashcards.http.response.TokenResponse
import com.flashcards.model.User

interface TokenProvider {
    fun createTokens(user: User): TokenResponse
    fun verifyToken(token: String): Int?
}