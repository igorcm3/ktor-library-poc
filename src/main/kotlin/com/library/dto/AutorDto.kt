package com.library.dto

import kotlinx.serialization.Serializable

@Serializable
data class AutorRequest(
    val nome: String,
    val nacionalidade: String? = null
)

@Serializable
data class AutorResponse(
    val id: Long,
    val nome: String,
    val nacionalidade: String?
)
