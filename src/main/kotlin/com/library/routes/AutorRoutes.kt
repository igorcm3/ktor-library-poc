package com.library.routes

import com.library.dto.AutorRequest
import com.library.service.AutorService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.autorRoutes(autorService: AutorService) {
    
    route("/api/autores") {
        get {
            val autores = autorService.getAll()
            call.respond(autores)
        }
        
        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@get
            }
            
            val autor = autorService.getById(id)
            if (autor == null) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Autor não encontrado"))
            } else {
                call.respond(autor)
            }
        }
        
        post {
            val request = call.receive<AutorRequest>()
            val autor = autorService.create(request)
            call.respond(HttpStatusCode.Created, autor)
        }
        
        put("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@put
            }
            
            val request = call.receive<AutorRequest>()
            val autor = autorService.update(id, request)
            if (autor == null) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Autor não encontrado"))
            } else {
                call.respond(autor)
            }
        }
        
        delete("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@delete
            }
            
            val deleted = autorService.delete(id)
            if (deleted) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Autor não encontrado"))
            }
        }
    }
}
