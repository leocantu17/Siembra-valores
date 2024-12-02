package com.example.siembravalores.data

import kotlinx.serialization.Serializable

data class SiembraValoresUiState(
    val correo:String="",
    val contrasena:String="",
    val autenticado: Boolean=false,
    val error:String="",
    val usuario:List<Usuario> = emptyList()
)

@Serializable
data class Usuario (
    val id: String? = null,
    val nombre: String? = null
)

