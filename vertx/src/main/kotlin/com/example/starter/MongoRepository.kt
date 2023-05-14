package com.example.starter

import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import io.vertx.kotlin.coroutines.await
import io.vertx.sqlclient.Row
import java.time.LocalDateTime
import java.util.*

class MongoRepository(private val mongoClient : MongoClient) {

    suspend fun findData(title: String): List<Post> {
        val query = JsonObject()
            .put("title", title)
        return mongoClient.find("test", query)
            .await()
            .map(mapFun)
    }

    val mapFun: (JsonObject) -> Post = {
        Post(UUID.fromString(it.getString("_id")),
             it.getString("title"),
             it.getString("content"),
             LocalDateTime.parse(it.getString("created_at")))
    }
}