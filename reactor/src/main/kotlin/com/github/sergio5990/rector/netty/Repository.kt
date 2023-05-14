package com.github.sergio5990.rector.netty

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.spi.Connection
import java.time.LocalDateTime
import java.util.UUID
import io.r2dbc.spi.Readable
import io.r2dbc.spi.Statement
import kotlinx.coroutines.reactive.*
import kotlinx.coroutines.reactor.awaitSingle
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class Repository(private val pool: ConnectionPool) {

    suspend fun findByTitle(title: String): List<Post> {
        val posts = query({
                              createStatement("select * from posts where title = $1")
                                  .bind("$1", title)
                          }, ::getMapFun)

        return posts.awaitSingle()
    }

    private fun <T> query(query: Connection.() -> Statement, mapper: (Readable) -> T): Mono<List<T>> {
        val function: (t: Connection) -> Mono<List<T>> = { connection ->
            Flux.from(connection.query().execute())
                .flatMap { it.map(mapper) }
                .collectList()
        }
        val posts = Mono.usingWhen(pool.create(), function, Connection::close)
        return posts
    }
}

fun getMapFun(readable:Readable): Post{
    return Post(readable.get("id", UUID::class.java)!!,
                readable.get("title", String::class.java)!!,
                readable.get("content", String::class.java)!!,
                readable.get("created_at", LocalDateTime::class.java)!!)
}