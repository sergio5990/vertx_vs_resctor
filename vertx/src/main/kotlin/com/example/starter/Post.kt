package com.example.starter

import io.vertx.sqlclient.Row
import java.time.LocalDateTime
import java.util.UUID

data class Post(val id: UUID,
                val title: String,
                val content: String,
                val createdAt: LocalDateTime)

val mapFun: (Row) -> Post = {
    Post(it.getUUID("id"),
         it.getString("title"),
         it.getString("content"),
         it.getLocalDateTime("created_at"))
}