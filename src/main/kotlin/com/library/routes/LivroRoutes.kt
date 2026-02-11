package com.library.routes

import com.library.dto.LivroRequest
import com.library.service.LivroService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.livroRoutes(livroService: LivroService) {
    
    route("/api/livros") {
        get {
            val livros = livroService.getAll()
            call.respond(livros)
        }
        
        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@get
            }
            
            val livro = livroService.getById(id)
            if (livro == null) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Livro não encontrado"))
            } else {
                call.respond(livro)
            }
        }
        
        post {
            val request = call.receive<LivroRequest>()
            val livro = livroService.create(request)
            call.respond(HttpStatusCode.Created, livro)
        }
        
        put("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@put
            }
            
            val request = call.receive<LivroRequest>()
            val livro = livroService.update(id, request)
            if (livro == null) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Livro não encontrado"))
            } else {
                call.respond(livro)
            }
        }
        
        delete("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@delete
            }
            
            val deleted = livroService.delete(id)
            if (deleted) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Livro não encontrado"))
            }
        }
    }
}
