package com.example.siembravalores.repository

import com.example.siembravalores.data.SiembraValoresUiState
import com.example.siembravalores.data.Usuario
import com.example.siembravalores.network.SiembraValoresApp

class Repositorio {
    private val api = SiembraValoresApp.retrofitService

    suspend fun obtenerUsuarios(correo:String,contrasena:String):List<Usuario> {
        return api.getUsuario(correo,contrasena)
    }


}