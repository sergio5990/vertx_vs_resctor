package com.github.sergio5990.rector.netty

import com.mongodb.client.model.Filters.eq
import com.mongodb.reactivestreams.client.MongoDatabase
import io.r2dbc.spi.Readable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.bson.Document
import java.time.LocalDateTime
import java.util.*

class MongoRepository(private val dataBase: MongoDatabase) {
    suspend fun findByTitle(title: String): List<Post> {
        val collection = dataBase.getCollection("test")
        return collection.find(eq("title", title))
            .asFlow()
            .map(::mongoMap)
            .toList()
    }

    fun mongoMap(document: Document): Post {
        return Post(UUID.fromString(document.get("_id", String::class.java)),
                    document.get("title", String::class.java)!!,
                    document.get("content", String::class.java)!!,
                    LocalDateTime.parse(document.get("created_at", String::class.java))
                   )
    }
}