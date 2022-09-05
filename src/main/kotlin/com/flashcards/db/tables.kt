package com.flashcards.db.dao

import com.flashcards.model.Card
import com.flashcards.model.Pack
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import java.util.*

object UsersTable : UUIDTable() {
    val username = varchar("username", length = 512)
    val email = varchar("email", length = 513)
    val password = text("password")
}

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(UsersTable)
    var username by UsersTable.username
    var email by UsersTable.email
    var password by UsersTable.password
}
object CardsTable : IntIdTable() {
    val pl = varchar("pl", 64)
    val en = varchar("en", 64)
}

class CardEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CardEntity>(CardsTable)

    var pl by CardsTable.pl
    var en by CardsTable.en

    var packs by PackEntity via CardsPacksJunctionTable

    fun toCard(): Card = Card(
        id = id.value, pl = pl, en = en
    )
}

object PacksTable : IntIdTable() {
    val name = varchar("name", 64)
}

class PackEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PackEntity>(PacksTable)

    var name by PacksTable.name
    var cards by CardEntity via CardsPacksJunctionTable

    fun toPack(): Pack = Pack(id = id.value, name = name, cards = cards.map { it.toCard() })
    fun addCards(cards: List<Card>) = cards.mapNotNull { it.id }.forEach { cardId ->
        CardsPacksJunctionTable.insert {
            it[card] = cardId
            it[pack] = id
        }
    }
}

object CardsPacksJunctionTable : Table() {
    val card = reference("card", CardsTable)
    val pack = reference("pack", PacksTable)
}

object UsersCardsJunctionTable : Table() {
    val card = reference("card", CardsTable)
    val pack = reference("user", UsersTable)
}

object UsersPacksJunctionTable : Table() {
    val user = reference("user", UsersTable)
    val pack = reference("pack", PacksTable)
}