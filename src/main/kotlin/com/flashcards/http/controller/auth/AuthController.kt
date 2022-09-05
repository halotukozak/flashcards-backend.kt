package com.flashcards.http.controller.Auth

import com.flashcards.http.controller.BaseController


class AuthControllerImpl : BaseController(), AuthController {

    override suspend fun createUser(credentials: RegisterRequest): ResponseUser {
        val user = dbQuery {
            authDAO.getUserByUsername(postUser.username)?.let {
                throw InvalidUserException("User is already taken")
            }
            userApi.createUser(postUser) ?: throw UnknownError("Internal server error")
        }
        return user.toResponseUser()
    }

    override suspend fun authenticate(credentials: LoginCredentials) = dbQuery {
        userApi.getUserByUsername(credentials.username)?.let { user ->
            if (passwordManager.validatePassword(credentials.password, user.password)) {
                val credentialsRe sponse = tokenProvider.createTokens(user)
                LoginTokenResponse(credentialsResponse)
            } else {
                throw AuthenticationException("Wrong credentials")
            }
        } ?: throw AuthenticationException("Wrong credentials")
    }

    override suspend fun refreshToken(credentials: RefreshBody) = dbQuery {
        tokenProvider.verifyToken(credentials.refreshToken)?.let {
            userApi.getUserById(it)?.let {
                val credentialsResponse = tokenProvider.createTokens(it)
                LoginTokenResponse(credentialsResponse)
            } ?: throw AuthenticationException("Wrong credentials")
        } ?: throw AuthenticationException("Wrong credentials")
    }
}

interface AuthController {
    suspend fun createUser(postUser: PostUserBody): ResponseUser
    suspend fun authenticate(credentials: LoginCredentials): LoginTokenResponse
    suspend fun refreshToken(credentials: RefreshBody): LoginTokenResponse
}