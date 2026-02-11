package com.library.repository

import com.library.model.Autores
import com.library.model.Livros
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class LivroRepository {
    
    fun findAll(): List<ResultRow> {
        return (Livros innerJoin Autores)
            .selectAll()
            .toList()
    }
    
    fun findById(id: Long): ResultRow? {
        return (Livros innerJoin Autores)
            .selectAll()
            .where { Livros.id eq id }
            .singleOrNull()
    }
    
    fun create(titulo: String, isbn: String, anoPublicacao: Int, autorId: Long): Long {
        return Livros.insertAndGetId {
            it[Livros.titulo] = titulo
            it[Livros.isbn] = isbn
            it[Livros.anoPublicacao] = anoPublicacao
            it[Livros.autorId] = autorId
        }.value
    }
    
    fun update(id: Long, titulo: String, isbn: String, anoPublicacao: Int, autorId: Long): Boolean {
        val updated = Livros.update({ Livros.id eq id }) {
            it[Livros.titulo] = titulo
            it[Livros.isbn] = isbn
            it[Livros.anoPublicacao] = anoPublicacao
            it[Livros.autorId] = autorId
        }
        return updated > 0
    }
    
    fun delete(id: Long): Boolean {
        val deleted = Livros.deleteWhere { Livros.id eq id }
        return deleted > 0
    }
}
