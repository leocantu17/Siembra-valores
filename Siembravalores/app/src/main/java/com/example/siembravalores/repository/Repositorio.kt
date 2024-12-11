package com.example.siembravalores.repository

import com.example.siembravalores.data.Arboles
import com.example.siembravalores.data.Notificacion
import com.example.siembravalores.data.Perfil
import com.example.siembravalores.data.Servicios
import com.example.siembravalores.data.Usuario
import com.example.siembravalores.data.Valores
import com.example.siembravalores.data.infoArbol
import com.example.siembravalores.data.misArbolesData
import com.example.siembravalores.network.SiembraValoresApp

class Repositorio {
    private val api = SiembraValoresApp.retrofitService

    suspend fun obtenerUsuarios(correo:String,contrasena:String):List<Usuario> {
        return api.getUsuario(correo,contrasena)
    }
    suspend fun obtenerArboles(id:Int):List<Arboles>{
        return api.getArboles(id)
    }
    suspend fun obtenerInfoArbol(id:Int):List<Arboles>{
        return api.getObtenerInformacion(id)
    }
    suspend fun adoptarArbol(ID_US:Int, ID_ARBOL:Int, ID_VALOR: String){
        return api.adoptarArbol(ID_US,ID_ARBOL,ID_VALOR)
    }
    suspend fun obtenerValores():List<Valores>{
        return api.getValores()
    }
    suspend fun misArboles(ID_US: Int):List<misArbolesData>{
        return api.getMisArboles(ID_US)
    }
    suspend fun misArbolesInfo(ID_ARBOL: Int):List<infoArbol>{
        return api.getInfoArbol(ID_ARBOL)
    }
    suspend fun servicios():List<Servicios>{
        return api.getServicio()
    }
    suspend fun perfil(ID_US: Int):List<Perfil>{
        return api.getPerfil(ID_US)
    }
    suspend fun agregarNot(){
        return api.agregarNotificaciones()
    }
    suspend fun getNotificaciones(ID_US:Int):List<Notificacion>{
        return api.obtenerNotificaciones(ID_US)
    }
    suspend fun notificacionLeida(ID_NOT:Int){
        return api.notificacionLeida(ID_NOT)
    }

}