package com.library.service

import com.library.dto.AutorResponse
import com.library.dto.LivroRequest
import com.library.dto.LivroResponse
import com.library.model.Autores
import com.library.model.Livros
import com.library.repository.LivroRepository
import org.jetbrains.exposed.sql.transactions.transaction

class LivroService(private val repository: LivroRepository) {
    
    fun getAll(): List<LivroResponse> = transaction {
        repository.findAll().map { row ->
            LivroResponse(
                id = row[Livros.id].value,
                titulo = row[Livros.titulo],
                isbn = row[Livros.isbn],
                anoPublicacao = row[Livros.anoPublicacao],
                autor = AutorResponse(
                    id = row[Autores.id].value,
                    nome = row[Autores.nome],
                    nacionalidade = row[Autores.nacionalidade]
                )
            )
        }
    }
    
    fun getById(id: Long): LivroResponse? = transaction {
        repository.findById(id)?.let { row ->
            LivroResponse(
                id = row[Livros.id].value,
                titulo = row[Livros.titulo],
                isbn = row[Livros.isbn],
                anoPublicacao = row[Livros.anoPublicacao],
                autor = AutorResponse(
                    id = row[Autores.id].value,
                    nome = row[Autores.nome],
                    nacionalidade = row[Autores.nacionalidade]
                )
            )
        }
    }
    
    fun create(request: LivroRequest): LivroResponse = transaction {
        val livroId = repository.create(
            request.titulo,
            request.isbn,
            request.anoPublicacao,
            request.autorId
        )
        getById(livroId)!!
    }
    
    fun update(id: Long, request: LivroRequest): LivroResponse? = transaction {
        if (repository.update(id, request.titulo, request.isbn, request.anoPublicacao, request.autorId)) {
            getById(id)
        } else null
    }
    
    fun delete(id: Long): Boolean = transaction {
        repository.delete(id)
    }
}
