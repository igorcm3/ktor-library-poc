package com.library.service

import com.library.dto.AutorRequest
import com.library.dto.AutorResponse
import com.library.model.Autores
import com.library.repository.AutorRepository
import org.jetbrains.exposed.sql.transactions.transaction

class AutorService(private val repository: AutorRepository) {
    
    fun getAll(): List<AutorResponse> = transaction {
        repository.findAll().map {
            AutorResponse(
                id = it[Autores.id].value,
                nome = it[Autores.nome],
                nacionalidade = it[Autores.nacionalidade]
            )
        }
    }
    
    fun getById(id: Long): AutorResponse? = transaction {
        repository.findById(id)?.let {
            AutorResponse(
                id = it[Autores.id].value,
                nome = it[Autores.nome],
                nacionalidade = it[Autores.nacionalidade]
            )
        }
    }
    
    fun create(request: AutorRequest): AutorResponse = transaction {
        val id = repository.create(request.nome, request.nacionalidade)
        AutorResponse(
            id = id,
            nome = request.nome,
            nacionalidade = request.nacionalidade
        )
    }
    
    fun update(id: Long, request: AutorRequest): AutorResponse? = transaction {
        if (repository.update(id, request.nome, request.nacionalidade)) {
            AutorResponse(
                id = id,
                nome = request.nome,
                nacionalidade = request.nacionalidade
            )
        } else null
    }
    
    fun delete(id: Long): Boolean = transaction {
        repository.delete(id)
    }
}
