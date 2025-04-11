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
import retrofit2.http.Field
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
private const val BASE_URL = "http://200.188.143.162:7019"

// Configuración de Retrofit más detallada
private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

// Interfaz de servicio con manejo de errores más robusto
interface SiembraValoresService {
    @POST("/siembra_valores/Leo.php")
    suspend fun getUsuario(
        @Query("endpoint") endPoint:String,
        @Query("correo") correo: String,
        @Query("contrasena") contrasena: String
    ): List<Usuario>

    @GET("/siembra_valores/Leo.php")
    suspend fun getArboles(
        @Query("id") ID: Int
    ): List<Arboles>

    @GET("/siembra_valores/Leo.php")
    suspend fun getObtenerInformacion(
        @Query("id") ID: Int
    ): List<Arboles>

    @POST("/siembra_valores/Leo.php")
    suspend fun adoptarArbol(
        @Query("ID_US") ID_US:Int,
        @Query("ID_ARBOL") ID_ARBOL:Int,
        @Query("ID_VALOR") ID_VALOR:String
    )

    @GET("/siembra_valores/Leo.php")
    suspend fun getValores():List<Valores>

    @GET("/siembra_valores/Leo.php")
    suspend fun getInfoArbol(
        @Query("endpoint") endPoint:String,
        @Query ("id_arbol") ID_ARBOL: Int
    ):List<infoArbol>

    @GET("/siembra_valores/Leo.php")
    suspend fun getMisArboles(
        @Query("endpoint") endPoint:String,
        @Query ("id_usuario") ID_US: Int
    ):List<misArbolesData>

    @GET("/siembra_valores/Leo.php")
    suspend fun getServicio(
        @Query("endpoint") endPoint: String
    ):List<Servicios>

    @GET("/siembra_valores/Leo.php")
    suspend fun getPerfil(
        @Query ("ID_US") ID_US: Int
    ):List<Perfil>

    @POST("/siembra_valores/Leo.php")
    suspend fun agregarNotificaciones()

    @GET ("/siembra_valores/Leo.php")
    suspend fun obtenerNotificaciones(
        @Query("ID_US") ID_US: Int
    ):List<Notificacion>

    @POST("/siembra_valores/Leo.php")
    suspend fun notificacionLeida(
        @Query("ID_NOT") ID_NOT:Int
    )

    @GET("/siembra_valores/Leo.php")
    suspend fun getHistorialServicios(
        @Query("endpoint") endPoint:String,
        @Query("ID_US") ID_US:Int,
        @Query("id_arbol") ID_ARBOL:Int
    ):List<HistorialServicios>

    @POST("/siembra_valores/Leo.php")
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
object SiembraValoresAppWeb {
    val retrofitService: SiembraValoresService by lazy {
        retrofit.create(SiembraValoresService::class.java)
    }
}