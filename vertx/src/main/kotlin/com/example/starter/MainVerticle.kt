package com.example.starter

import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.launch

class MainVerticle(private val dataHandler: DataHandler) : CoroutineVerticle() {

    override suspend fun start() {
        val router = Router.router(vertx)
        router.get("/posts")
            .produces("application/json")
            .coroutineHandler(dataHandler::testData)
        router.get("/check")
            .produces("plain/text")
            .coroutineHandler(dataHandler::check)
        router.get("/postsMongo")
            .produces("application/json")
            .coroutineHandler(dataHandler::testMongoData)

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8889)
            .onComplete {
                println("HttpSever started at ${it.result().actualPort()}")
            }
            .await()
    }

    private fun Route.coroutineHandler(fn: suspend (RoutingContext) -> Unit) = handler {
        val coroutineDispatcher = it.vertx().dispatcher()
        launch(coroutineDispatcher) {
            try {
                fn(it)
            } catch (e: Exception) {
                it.fail(e)
            }
        }
    }
}
