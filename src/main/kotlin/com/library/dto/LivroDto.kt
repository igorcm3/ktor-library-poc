package com.library.dto

import kotlinx.serialization.Serializable

@Serializable
data class LivroRequest(
    val titulo: String,
    val isbn: String,
    val anoPublicacao: Int,
    val autorId: Long
)

@Serializable
data class LivroResponse(
    val id: Long,
    val titulo: String,
    val isbn: String,
    val anoPublicacao: Int,
    val autor: AutorResponse
)
