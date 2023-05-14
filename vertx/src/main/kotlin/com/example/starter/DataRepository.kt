package com.example.starter

import io.vertx.kotlin.coroutines.await
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.SqlClient
import io.vertx.sqlclient.Tuple
import java.util.*

class DataRepository(private val client: SqlClient) {

    suspend fun findData(title: String): List<Post> {
        return client.preparedQuery("SELECT * FROM posts WHERE title = $1")
            .execute(Tuple.of(title))
            .map { it.map(mapFun) }
            .await()
    }
}