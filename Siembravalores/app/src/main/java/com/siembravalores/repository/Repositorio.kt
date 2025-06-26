package com.siembravalores.repository

import android.graphics.Bitmap
import com.siembravalores.data.CambioContrasenaResponse
import com.siembravalores.data.HistorialServicios
import com.siembravalores.data.Notificacion
import com.siembravalores.data.Perfil
import com.siembravalores.data.Servicios
import com.siembravalores.data.Usuario
import com.siembravalores.data.VerificacionResponse
import com.siembravalores.data.infoArbol
import com.siembravalores.data.misArbolesData
import com.siembravalores.network.SiembraValoresAppWeb
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class Repositorio {
    private val api = SiembraValoresAppWeb.retrofitService

    suspend fun obtenerUsuarios(correo:String,contrasena:String):List<Usuario> {
        return api.getUsuario("login",correo,contrasena)
    }
    suspend fun recuperarContrasena(correo: String){
        return api.recuperarContrasena(correo)
    }
    suspend fun verificarCodigo(correo: String, codigo: String): VerificacionResponse? {
        return try {
            val response = api.verificarCodigo(correo, codigo)

            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun cambiarContrasena(correo: String, nuevaContrasena: String): CambioContrasenaResponse? {
        return try {
            val response = api.cambiarContrasena(correo, nuevaContrasena)

            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    suspend fun misArboles(ID_US: Int):List<misArbolesData>{
        return api.getMisArboles("mis_arboles",ID_US)
    }
    suspend fun misArbolesInfo(ID_ARBOL: Int):List<infoArbol>{
        return api.getInfoArbol("info_arbol",ID_ARBOL)
    }
    suspend fun servicios():List<Servicios>{
        return api.getServicio("servicios")
    }
    suspend fun perfil(ID_US: Int):List<Perfil>{
        return api.getPerfil("ver_perfil_usuario",ID_US)
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
    suspend fun obtenerHistorialServicios(ID_US: Int,ID_ARBOL:Int):List<HistorialServicios>{
        return api.getHistorialServicios("historial_servicios",ID_US,ID_ARBOL)
    }
    suspend fun guardarDetallesServicio(servicioId:Int,comentarios:String,altura:Float,circunferencia:Float,ID_US:Int,ID_ARBOL: Int){
        return api.agregarServicioApp("servicio_agregar",servicioId,comentarios,altura,circunferencia,ID_US,ID_ARBOL)
    }
    suspend fun guardarDetallesMedicionServicio(
        servicioId: Int,
        comentarios: String,
        altura: Float,
        circunferencia: Float,
        ID_US: Int,
        ID_ARBOL: Int,
        bitmap: Bitmap
    ) {
        val imagenPart = bitmapToMultipart(bitmap)

        return api.agregarServicioMedicionApp(
            servicioId = stringToRequestBody(servicioId.toString()),
            comentarios = stringToRequestBody(comentarios),
            altura = stringToRequestBody(altura.toString()),
            circunferencia = stringToRequestBody(circunferencia.toString()),
            ID_US = stringToRequestBody(ID_US.toString()),
            ID_ARBOL = stringToRequestBody(ID_ARBOL.toString()),
            imagen = imagenPart
        )
    }

    fun bitmapToMultipart(bitmap: Bitmap): MultipartBody.Part {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val reqFile = bos.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("imagen", "foto.jpg", reqFile)
    }

    fun stringToRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }



}


