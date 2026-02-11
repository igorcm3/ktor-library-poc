package com.library.config

import com.library.model.Autores
import com.library.model.Livros
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect(
            url = "jdbc:h2:mem:library;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
            driver = "org.h2.Driver"
        )
        
        transaction {
            SchemaUtils.create(Autores, Livros)
        }
    }
}
