package com.library.config

import com.library.repository.AutorRepository
import com.library.repository.LivroRepository
import com.library.service.AutorService
import com.library.service.LivroService
import org.koin.dsl.module
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Koin")

val appModule = module {
    // Repositories
    single { 
        logger.info("📦 Registrando AutorRepository")
        AutorRepository() 
    }
    single { 
        logger.info("📦 Registrando LivroRepository")
        LivroRepository() 
    }
    
    // Services
    single { 
        logger.info("⚙️  Registrando AutorService")
        AutorService(get()) 
    }
    single { 
        logger.info("⚙️  Registrando LivroService")
        LivroService(get()) 
    }
}
