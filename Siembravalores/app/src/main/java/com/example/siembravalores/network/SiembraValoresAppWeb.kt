package com.example.siembravalores.network

import com.example.siembravalores.data.Arboles
import com.example.siembravalores.data.HistorialServicios
import com.example.siembravalores.data.Notificacion
import com.example.siembravalores.data.Perfil
import com.example.siembravalores.data.Servicios
import com.example.siembravalores.data.Usuario
import com.example.siembravalores.data.Valores
import com.example.siembravalores.data.infoArbol
import com.example.siembravalores.data.misArbolesData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

// Configuración más robusta de JSON
private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = true
    encodeDefaults = true
}


// Configuración de OkHttpClient más robusta
private val client = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)  // Aumentado a 60 segundos
    .readTimeout(60, TimeUnit.SECONDS)     // Aumentado a 60 segundos
    .writeTimeout(60, TimeUnit.SECONDS)    // Agregado timeout de escritura
    .hostnameVerifier { _, _ -> true }     // Ignorar verificación de hostname (solo para desarrollo)
    .build()

// URL base con posibilidad de cambiar fácilmente
private const val BASE_URL = "http://172.16.185.237:3000"

// Configuración de Retrofit más detallada
private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

// Interfaz de servicio con manejo de errores más robusto
interface SiembraValoresService {
    @POST("/rt-iniciar-sesion-app")
    suspend fun getUsuario(
        @Query("correo") correo: String,
        @Query("contrasena") contrasena: String
    ): List<Usuario>

    @GET("/rt-arboles-disponibles")
    suspend fun getArboles(
        @Query("id") ID: Int
    ): List<Arboles>

    @GET("/rt-informacion-arbol")
    suspend fun getObtenerInformacion(
        @Query("id") ID: Int
    ): List<Arboles>

    @POST("/rt-adoptar-arbol")
    suspend fun adoptarArbol(
        @Query("ID_US") ID_US:Int,
        @Query("ID_ARBOL") ID_ARBOL:Int,
        @Query("ID_VALOR") ID_VALOR:String
    )

    @GET("/rt-obtener-valores")
    suspend fun getValores():List<Valores>

    @GET("/rt-obtener-info-arbol")
    suspend fun getInfoArbol(
        @Query ("ID_ARBOL") ID_ARBOL: Int
    ):List<infoArbol>

    @GET("/rt-mis-arboles")
    suspend fun getMisArboles(
        @Query ("ID_US") ID_US: Int
    ):List<misArbolesData>

    @GET("/rt-obtener-servicios")
    suspend fun getServicio():List<Servicios>

    @GET("/rt-perfil-usuario")
    suspend fun getPerfil(
        @Query ("ID_US") ID_US: Int
    ):List<Perfil>

    @POST("/rt-agregar-notificaciones")
    suspend fun agregarNotificaciones()

    @GET ("/rt-notificaciones-alumno")
    suspend fun obtenerNotificaciones(
      @Query("ID_US") ID_US: Int
    ):List<Notificacion>

    @POST("/rt-notificacion-leida")
    suspend fun notificacionLeida(
        @Query("ID_NOT") ID_NOT:Int
    )

    @GET("/rt-historial-servicios")
    suspend fun getHistorialServicios(
        @Query("ID_US") ID_US:Int,
        @Query("ID_ARBOL") ID_ARBOL:Int
    ):List<HistorialServicios>

    @POST("/rt-agregar-servicio")
    suspend fun agregarServicioApp(
        @Query("ID_SERVICIO") servicioId:Int,
        @Query("COMENTARIOS") comentarios:String,
        @Query("ALTURA") altura:Float,
        @Query("CIRCUNFERENCIA") circunferencia:Float,
        @Query("ID_US") ID_US: Int,
        @Query("ID_ARBOL") ID_ARBOL: Int
    )
}

// Objeto singleton para acceder al servicio
object SiembraValoresApp {
    val retrofitService: SiembraValoresService by lazy {
        retrofit.create(SiembraValoresService::class.java)
    }
}
