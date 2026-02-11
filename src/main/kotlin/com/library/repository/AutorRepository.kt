package com.library.repository

import com.library.model.Autores
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class AutorRepository {
    
    fun findAll(): List<ResultRow> = Autores.selectAll().toList()
    
    fun findById(id: Long): ResultRow? = Autores.selectAll().where { Autores.id eq id }.singleOrNull()
    
    fun create(nome: String, nacionalidade: String?): Long {
        return Autores.insertAndGetId {
            it[Autores.nome] = nome
            it[Autores.nacionalidade] = nacionalidade
        }.value
    }
    
    fun update(id: Long, nome: String, nacionalidade: String?): Boolean {
        val updated = Autores.update({ Autores.id eq id }) {
            it[Autores.nome] = nome
            it[Autores.nacionalidade] = nacionalidade
        }
        return updated > 0
    }
    
    fun delete(id: Long): Boolean {
        val deleted = Autores.deleteWhere { Autores.id eq id }
        return deleted > 0
    }
}
