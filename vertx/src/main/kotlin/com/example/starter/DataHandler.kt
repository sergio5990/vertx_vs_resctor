package com.example.starter

import com.fasterxml.jackson.databind.json.JsonMapper
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.await

class DataHandler(private val repository: DataRepository,
                  private val mongoRepository: MongoRepository) {

    suspend fun testData(ctx: RoutingContext) {
        val title = ctx.queryParam("title").first()
        val posts = repository.findData(title)
        ctx.response().end(Json.encode(posts)).await()
    }

    suspend fun testMongoData(ctx: RoutingContext) {
        val title = ctx.queryParam("title").first()
        val posts = mongoRepository.findData(title)
        ctx.response().end(Json.encode(posts)).await()
    }

    suspend fun check(ctx: RoutingContext){
        ctx.response()
            .end("ok")
            .await()
    }
}