package com.library.model

import org.jetbrains.exposed.dao.id.LongIdTable

object Autores : LongIdTable("autores") {
    val nome = varchar("nome", 255)
    val nacionalidade = varchar("nacionalidade", 100).nullable()
}

object Livros : LongIdTable("livros") {
    val titulo = varchar("titulo", 255)
    val isbn = varchar("isbn", 50).uniqueIndex()
    val anoPublicacao = integer("ano_publicacao")
    val autorId = reference("autor_id", Autores)
}
