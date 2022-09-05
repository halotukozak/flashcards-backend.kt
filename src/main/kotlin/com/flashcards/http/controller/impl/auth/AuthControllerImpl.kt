package com.flashcards.http.controller.auth

import com.flashcards.db.dao.impl.AuthDAOImpl
import com.flashcards.exceptions.AuthenticationException
import com.flashcards.exceptions.WrongCredentialsException
import com.flashcards.http.request.RefreshTokenRequest
import com.flashcards.http.request.auth.LoginRequest
import com.flashcards.http.request.auth.RegisterRequest
import com.flashcards.http.response.TokenResponse
import jBCrypt.BCrypt
import kotlinx.coroutines.runBlocking

class AuthControllerImpl(private val tokenProvider: TokenProvider) : AuthController {
    private val authDAO = AuthDAOImpl().apply {
        runBlocking {}
    }

    override suspend fun createUser(registerCredentials: RegisterRequest): TokenResponse {

        val isUsernameTaken = authDAO.isUsernameTaken(registerCredentials.username)
        if (isUsernameTaken) throw AuthenticationException("User is already taken")

        val user = authDAO.registerUser(
            username = registerCredentials.username,
            email = registerCredentials.email,
            password = (encryptPassword(registerCredentials.password))
        )
        val credentialsResponse = tokenProvider.createTokens(user)
        return (credentialsResponse)
    }

    override suspend fun authenticate(loginCredentials: LoginRequest): TokenResponse {
        authDAO.findUserByEmail(loginCredentials.email)?.let { user ->
            if (validatePassword(loginCredentials.password, user.password)) {
                val credentialsResponse = tokenProvider.createTokens(user)
                return (credentialsResponse)
            } else {
                throw WrongCredentialsException()
            }
        } ?: throw WrongCredentialsException()
    }

    override suspend fun refreshToken(tokenCredentials: RefreshTokenRequest): TokenResponse {
        tokenProvider.verifyToken(tokenCredentials.refreshToken)?.let { id ->
            authDAO.findUserById(id)?.let {
                val credentialsResponse = tokenProvider.createTokens(it)
                return (credentialsResponse)
            } ?: throw AuthenticationException("Wrong credentials")
        } ?: throw AuthenticationException("Wrong credentials")
    }

    override suspend fun validatePassword(expectedPassword: String, providedPassword: String): Boolean =
        BCrypt.checkpw(expectedPassword, providedPassword)

    override suspend fun encryptPassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())
}