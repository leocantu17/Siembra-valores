package com.example.siembravalores.network

import com.example.siembravalores.data.Usuario
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


private const val BASE_URL="http://192.168.0.7:3000"

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

private val retrofit=Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface IniciarSesion{
    @GET("/rt-prueba")
    suspend fun getUsuario(
        @Query("correo") correo:String,
        @Query("contrasena") contrasena:String
    ):List<Usuario>
}

object SiembraValoresApp{
    val retrofitService: IniciarSesion by lazy {
        retrofit.create(IniciarSesion::class.java)
    }
}
