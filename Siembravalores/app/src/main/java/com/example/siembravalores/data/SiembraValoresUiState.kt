package com.example.siembravalores.data

import kotlinx.serialization.Serializable

data class SiembraValoresUiState(
    val correo: String ="",
    val contrasena: String ="",
    val autenticado: Boolean =false,
    val error: String? ="",
    val id_Arbol: Int =0,
    val id_Us: Int =0,
    val usuario: List<Usuario> = emptyList(),
    val misArboles: List<misArbolesData> = emptyList(),
    val arbolesInfo: List<infoArbol> = emptyList(),
    val servicios: List<Servicios> = emptyList(),
    val perfil: List<Perfil> = emptyList(),
    val notificaciones: List<Notificacion> = emptyList(),
    val historialServicios: List<HistorialServicios> = emptyList(),
    val id_servicio: Int =0,
    var isLoading: Boolean =false,
    val correoRecuperacion: String = ""
)

@Serializable
data class Usuario (
    val ID_US: Int,
    val NOMBRE: String? = null
)

@Serializable
data class infoArbol(
    val ID_ARBOL:Int=0,
    val NOMBRE_CIENTIFICO:String?=null,
    val FECHA_PLANTADO: String?=null,
    val DESCRIPCION: String?=null,
    val ENDEMICO:Boolean=false,
    val VALOR_ASOCIADO:String?=null,
    val COLONIA: String?=null
)

@Serializable
data class misArbolesData(
    val ID_ARBOL: Int=0,
    val VALOR_ASOCIADO: String?=null,
    val DESCRIPCION: String?=null
)

@Serializable
data class Servicios(
    val ID_SERVICIO: Int=0,
    val TIPO:String?=null
)

@Serializable
data class Perfil(
    val PUNTOS:String?=null,
    val NOMBRE:String?=null,
    val AP_P:String?=null,
    val AP_M:String?=null,
    val CORREO:String?=null,
    val FECHA_NAC:String?=null,
    val FECHA_REGISTRO:String?=null,
    val ESCUELA:String?=null,
    val CELULAR:String?=null,
    val TIPO: String?=null
)

@Serializable
data class Notificacion(
    val ID_NOTIFIACION:Int=0,
    val MENSAJE:String?=null,
    val FECHA_ENVIO:String?=null,
    val VALOR:String?=null
)

@Serializable
data class HistorialServicios(
    val NOMBRE_SERVICIO: String?=null,
    val FECHA_SERVICIO:String?=null,
    val COMENTARIOS:String?=null
)


