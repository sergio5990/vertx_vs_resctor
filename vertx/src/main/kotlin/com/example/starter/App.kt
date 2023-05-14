package com.example.starter

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.core.json.jackson.DatabindCodec
import io.vertx.ext.mongo.MongoClient
import io.vertx.kotlin.coroutines.await
import io.vertx.pgclient.PgConnectOptions
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.PoolOptions
import io.vertx.sqlclient.SqlClient
import java.time.Duration
import java.time.Instant


suspend fun main() {
    val start = Instant.now()

    val vertx = Vertx.vertx()
    configureObjectMapper()

    val sqlClient = pool(vertx)
    val repository = DataRepository(sqlClient)


    val config = JsonObject()
        .put("connection_string","mongodb://localhost:27017")
        .put("db_name","mydb")
    val client: MongoClient = MongoClient.createShared(vertx, config)
    val mongoRepository = MongoRepository(client)

    val dataHandler = DataHandler(repository, mongoRepository)
    vertx.deployVerticle(MainVerticle(dataHandler)).await()

    val startUpTime = Duration.between(start, Instant.now())
    println("App  started at $startUpTime")
}

fun configureObjectMapper() {
    val modules = listOf(kotlinModule(), JavaTimeModule())
    val mapper = DatabindCodec.mapper()
    with(mapper) {
        registerModules(modules)
        enable(SerializationFeature.INDENT_OUTPUT)
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }
}

private fun pool(vertx: Vertx): SqlClient {
    val connectOptions = PgConnectOptions().setPort(5432)
        .setHost("db")
        .setDatabase("postgres")
        .setUser("user")
        .setPassword("password")
    val poolOptions = PoolOptions().setMaxSize(5)

    return PgPool.client(vertx, connectOptions, poolOptions)
}