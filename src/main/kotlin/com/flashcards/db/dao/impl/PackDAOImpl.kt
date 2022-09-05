package com.flashcards.db.dao.impl

import com.flashcards.db.CardEntity
import com.flashcards.db.CardsPacksJunctionTable
import com.flashcards.db.PackEntity
import com.flashcards.db.dao.PackDAO
import com.flashcards.db.dbQuery
import com.flashcards.model.Card
import com.flashcards.model.Pack
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.insert

class PackDAOImpl : PackDAO {


    override suspend fun getPackById(id: Int, withCards: Boolean): Pack? = dbQuery {
        PackEntity.findById(id)?.toPack()
    }

    private fun Pack.withCards() = PackEntity.findById(id!!)?.load(com.flashcards.db.PackEntity::cards)?.toPack()

    override suspend fun addNewPack(name: String, isPrivate: Boolean, cards: List<Card>): Pack = dbQuery {

        val pack = PackEntity.new {
            this.name = name
            this.isPrivate = isPrivate
        }
        cards.forEach { card ->
            val cardId = CardEntity.new {
                pl = card.pl
                en = card.en
            }.id
            CardsPacksJunctionTable.insert {
                it[CardsPacksJunctionTable.card] = cardId
                it[CardsPacksJunctionTable.pack] = pack.id
            }
        }
        pack.toPack()
    }

//
//    override suspend fun PackEntity.addCard(card: Card): CardEntity {
//        TODO()
//    }
//
//    override suspend fun PackEntity.addCards(cards: List<Card>): List<CardEntity> {
//        TODO("Not yet implemented")
//    }


    override suspend fun editPack(id: Int, name: String): Pack? = dbQuery {
        PackEntity.findById(id)?.apply {
            this.name = name
        }?.toPack()
    }

    override suspend fun deletePack(id: Int): Boolean = dbQuery {
        PackEntity.findById(id)?.delete() != null
    }


}
val packDAO = PackDAOImpl().apply {
    runBlocking {}
}