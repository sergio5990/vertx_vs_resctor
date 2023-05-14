package com.github.sergio5990.rector.netty

import com.fasterxml.jackson.databind.json.JsonMapper
import io.netty.handler.codec.http.QueryStringDecoder
import reactor.netty.http.server.HttpServerRequest

class Handler(private val repository: Repository,
              private val mongoRepository: MongoRepository,
              private val mapper: JsonMapper) {

    suspend fun handle(rq: HttpServerRequest) : String {
        val title = extractTitle(rq)
        val posts = repository.findByTitle(title)
        return mapper.writeValueAsString(posts)
    }

    suspend fun handleMongo(rq: HttpServerRequest) : String {
        val title = extractTitle(rq)
        val posts = mongoRepository.findByTitle(title)
        return mapper.writeValueAsString(posts)
    }

    private fun extractTitle(rq: HttpServerRequest): String {
        val decoder = QueryStringDecoder(rq.uri())
        val title = decoder.parameters()
            .getValue("title")
            .first()
        return title
    }
}