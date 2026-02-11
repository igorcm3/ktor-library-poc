package com.library

import com.library.routes.autorRoutes
import com.library.routes.livroRoutes
import com.library.routes.webRoutes
import com.library.service.AutorService
import com.library.service.LivroService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val autorService by inject<AutorService>()
    val livroService by inject<LivroService>()
    
    routing {
        webRoutes(autorService, livroService)
        autorRoutes(autorService)
        livroRoutes(livroService)
    }
}
