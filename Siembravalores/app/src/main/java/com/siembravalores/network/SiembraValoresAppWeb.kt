package com.siembravalores.network

import com.siembravalores.data.CambioContrasenaResponse
import com.siembravalores.data.HistorialServicios
import com.siembravalores.data.Notificacion
import com.siembravalores.data.Perfil
import com.siembravalores.data.Servicios
import com.siembravalores.data.Usuario
import com.siembravalores.data.VerificacionResponse
import com.siembravalores.data.infoArbol
import com.siembravalores.data.misArbolesData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @POST("/siembra_valores/RecuperarContra.php")
    suspend fun recuperarContrasena(
        @Query("correo") correo: String
    )
    @POST("/siembra_valores/VerificarCodigo.php")
    suspend fun verificarCodigo(
        @Query("correo") correo: String,
        @Query("codigo") codigo: String
    ): Response<VerificacionResponse>

    @POST("/siembra_valores/CambiarContra.php")
    suspend fun cambiarContrasena(
        @Query("correo") correo: String,
        @Query("nueva_contrasena") nuevaContrasena: String
    ): Response<CambioContrasenaResponse>

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
        @Query("endpoint") endPoint: String,
        @Query ("id_us") ID_US: Int
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
        @Query("endpoint") endPoint: String,
        @Query("id_servicio") servicioId:Int,
        @Query("comentarios") comentarios:String,
        @Query("ALTURA") altura:Float,
        @Query("CIRCUNFERENCIA") circunferencia:Float,
        @Query("ID_US") ID_US: Int,
        @Query("id_arbol") ID_ARBOL: Int
    )

    @Multipart
    @POST("/siembra_valores/Medicion.php")
    suspend fun agregarServicioMedicionApp(
        @Part("id_servicio") servicioId: RequestBody,
        @Part("comentarios") comentarios: RequestBody,
        @Part("ALTURA") altura: RequestBody,
        @Part("CIRCUNFERENCIA") circunferencia: RequestBody,
        @Part("ID_US") ID_US: RequestBody,
        @Part("id_arbol") ID_ARBOL: RequestBody,
        @Part imagen: MultipartBody.Part
    )


}

// Objeto singleton para acceder al servicio
object SiembraValoresAppWeb {
    val retrofitService: SiembraValoresService by lazy {
        retrofit.create(SiembraValoresService::class.java)
    }
}