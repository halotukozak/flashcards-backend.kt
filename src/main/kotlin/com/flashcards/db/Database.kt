package com.flashcards.plugins

import com.flashcards.db.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.configureDatabase() {
    Database.connect(
        url = environment.config.propertyOrNull("database.url")?.getString() ?: "",
        user = environment.config.propertyOrNull("database.user")?.getString() ?: "",
        password = environment.config.propertyOrNull("database.password")?.getString() ?: ""
    )

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(
            CardsTable, PacksTable, UsersTable,
            CardsPacksJunctionTable, UsersCardsJunctionTable, UsersPacksJunctionTable
        )
    }
}

