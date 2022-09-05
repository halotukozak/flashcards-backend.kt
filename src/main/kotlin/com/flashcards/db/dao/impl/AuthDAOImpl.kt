package com.flashcards.db.dao.impl

import com.flashcards.db.dao.AuthDAO
import com.flashcards.model.User
import java.util.*

class AuthDAOImpl : AuthDAO {
    override fun registerUser(username: String, email: String, password: String): User {
        TODO("Not yet implemented")
    }

    override fun isEmailTaken(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun findUserById(uuid: UUID): User? {
        TODO("Not yet implemented")
    }

    override fun loginUser(email: String, password: String): User? {
        TODO("Not yet implemented")
    }
}