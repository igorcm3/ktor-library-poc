package com.library.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("StatusPages")

fun Application.configureStatusPages() {
    logger.info("⚠️  Configurando tratamento de erros...")
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            logger.error("❌ Erro não tratado: ${cause.message}", cause)
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf(
                    "error" to "Internal server error",
                    "message" to (cause.message ?: "Unknown error")
                )
            )
        }
    }
}
