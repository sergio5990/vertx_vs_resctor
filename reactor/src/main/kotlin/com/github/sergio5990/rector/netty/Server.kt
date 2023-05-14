package com.github.sergio5990.rector.netty

import reactor.netty.DisposableServer
import reactor.netty.http.server.HttpServer
import reactor.netty.http.server.HttpServerRequest
import java.time.Duration
import java.time.Instant
import kotlinx.coroutines.reactor.mono

fun runServer(start: Instant,
              code: suspend (HttpServerRequest) -> String,
              codeMongo: suspend (HttpServerRequest) -> String) {
    val server: DisposableServer = HttpServer.create()
        .route { routes ->
            routes.get("/posts") { rq, rs ->
                rs.sendString(mono { code(rq) })
            }
            routes.get("/check") { rq, rs ->
                rs.sendString(mono { "ok" })
            }
            routes.get("/postsMongo") { rq, rs ->
                rs.sendString(mono { codeMongo(rq) })
            }
        }
        .port(8888)
        .doOnBind {
            val startUpTime = Duration.between(start, Instant.now())
            println("App  started at $startUpTime")
        }
        .bindNow()

    server.onDispose()
        .block()
}
