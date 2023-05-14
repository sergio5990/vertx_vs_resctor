package com.github.sergio5990.rector.netty

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.mongodb.MongoCredential
import com.mongodb.reactivestreams.client.MongoClients
import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.ConnectionFactoryOptions.*
import java.time.Duration
import java.time.Instant


fun main() {
    Thread.sleep(10_000)
    val start = Instant.now()
    val mapper = defaultObjectMapper()

    val pool = pool()
    val repository = Repository(pool)

    val mongoClient = MongoClients.create("mongodb://localhost:27017")
    val database = mongoClient.getDatabase("mydb")
    val mongoRepository = MongoRepository(database)

    val handler = Handler(repository, mongoRepository,  mapper)
    runServer(start, handler::handle, handler::handleMongo)
}

fun defaultObjectMapper() = jsonMapper {
    addModule(kotlinModule())
    addModule(JavaTimeModule())
    enable(SerializationFeature.INDENT_OUTPUT)
    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
}

fun pool(): ConnectionPool {
    val connectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
                                                        .option(DRIVER, "postgresql")
                                                        .option(HOST, "db")
                                                        .option(PORT, 5432)
                                                        .option(USER, "user")
                                                        .option(PASSWORD, "password")
                                                        .option(DATABASE, "postgres")
                                                        .build())

    val configuration = ConnectionPoolConfiguration.builder(connectionFactory)
        .maxIdleTime(Duration.ofMillis(1000))
        .maxSize(5)
        .build()

    return ConnectionPool(configuration)
}