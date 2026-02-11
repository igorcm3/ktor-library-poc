package com.library

import com.library.config.DatabaseFactory
import com.library.plugins.*
import io.ktor.server.application.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Application")

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    logger.info("🚀 Iniciando BiblioTech...")
    
    DatabaseFactory.init()
    configureKoin()
    configureSerialization()
    configureStatusPages()
    configureSwagger()
    configureRouting()
    
    logger.info("✅ Aplicação pronta em http://localhost:8080")
}
