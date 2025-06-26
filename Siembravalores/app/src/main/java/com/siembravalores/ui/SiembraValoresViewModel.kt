package com.siembravalores.ui

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siembravalores.data.SiembraValoresUiState
import com.siembravalores.repository.Repositorio
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.util.Base64
import java.io.ByteArrayOutputStream

class SiembraValoresViewModel(private val repository:Repositorio=Repositorio()): ViewModel() {
    private val _uiState= MutableStateFlow(SiembraValoresUiState())
    val uiState: StateFlow<SiembraValoresUiState> =_uiState.asStateFlow()

    var correo by mutableStateOf("")
        private set
    var contrasena by mutableStateOf("")
        private set

    var seguir by mutableStateOf(true)
        private set

    fun updateCorreo(correoUsuario:String){
        correo=correoUsuario
        _uiState.update { currentState->
            currentState.copy(correo=correoUsuario)
        }
    }
    fun updateCorreoRec(correoUsuarioRecuperacion:String){
        correo=correoUsuarioRecuperacion
        _uiState.update { currentState->
            currentState.copy(correoRecuperacion = correoUsuarioRecuperacion)
        }
    }

    fun updateContrasena(contrasenaUsuario:String){
        contrasena=contrasenaUsuario
        _uiState.update { currentState->
            currentState.copy(contrasena=contrasenaUsuario)
        }
    }
//    init{
//        notificacionesPeriodicas()
//        notificacionesAlumno()
//    }
fun RecuperarContrasena(correoRecuperacion:String){
    viewModelScope.launch {
        try {
            repository.recuperarContrasena(correoRecuperacion)
        }catch (e: Exception) {
            _uiState.value = uiState.value.copy(
                autenticado = false,
                error = e.localizedMessage ?: "Error de autenticación"
            )
        }
    }
}
    // Agregar estas funciones a tu SiembraValoresViewModel

    fun updateCodigoVerificacion(codigo: String) {
        _uiState.update { currentState ->
            currentState.copy(codigoVerificacion = codigo)
        }
    }

    fun updateNuevaContrasena(contrasena: String) {
        _uiState.update { currentState ->
            currentState.copy(nuevaContrasena = contrasena)
        }
    }

    fun updateConfirmarContrasena(contrasena: String) {
        _uiState.update { currentState ->
            currentState.copy(confirmarContrasena = contrasena)
        }
    }


    // 2. MEJORAR EL VIEWMODEL CON MEJOR MANEJO DE ERRORES
    fun verificarCodigo() {
        viewModelScope.launch {
            try {
                val state = _uiState.value
                val correo = if (state.correo == "") state.correoRecuperacion else state.correo


                // Validaciones previas
                if (correo.isEmpty()) {
                    _uiState.value = state.copy(error = "Correo no válido")
                }

                if (state.codigoVerificacion.isEmpty()) {
                    _uiState.value = state.copy(error = "Código no válido")
                }

                _uiState.value = state.copy(isLoading = true, error = null)

                val response = repository.verificarCodigo(correo, state.codigoVerificacion)

                if (response?.success == true) {
                    _uiState.value = state.copy(
                        codigoVerificado = true,
                        isLoading = false,
                        error = null
                    )
                } else {
                    _uiState.value = state.copy(
                        isLoading = false,
                        error = response?.message ?: "Error al verificar código"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error inesperado: ${e.message}"
                )
            }
        }
    }

    fun cambiarContrasena() {
        viewModelScope.launch {
            try {
                val state = _uiState.value

                // Validaciones
                if (state.nuevaContrasena != state.confirmarContrasena) {
                    _uiState.value = state.copy(error = "Las contraseñas no coinciden")
                }

                if (state.nuevaContrasena.length < 8) {
                    _uiState.value = state.copy(error = "La contraseña debe tener al menos 8 caracteres")
                }

                _uiState.value = state.copy(isLoading= true, error = null)
                val correo = if (state.correo == "") state.correoRecuperacion else state.correo
                val response = repository.cambiarContrasena(correo, state.nuevaContrasena)

                if (response?.success == true) {
                    _uiState.value = state.copy(
                        contrasenaActualizada = true,
                        nuevaContrasena = "",
                        confirmarContrasena = "",
                        codigoVerificacion = "",
                        isLoading = false,
                        error = null
                    )
                } else {
                    _uiState.value = state.copy(
                        isLoading = false,
                        error = response?.message ?: "Error al cambiar contraseña"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error inesperado: ${e.message}"
                )
            }
        }
    }

    fun autenticarUsuario(correo: String, contrasena: String) {
        viewModelScope.launch {
            try {
                val usuarios = repository.obtenerUsuarios(correo, contrasena)

                if (usuarios.isNotEmpty()) {
                    val primerId = usuarios.first().ID_US
                    val fechaCon = usuarios.first().FECHA_CON

                    if (fechaCon != null) {
                        _uiState.value = uiState.value.copy(
                            id_Us = primerId,
                            usuario = usuarios, // ← AGREGAR ESTA LÍNEA
                            autenticado = true,
                            isLoading = false,  // ← También detener el loading
                            error = null        // ← Limpiar errores previos
                        )
                    } else {
                        _uiState.value = uiState.value.copy(
                            usuario = usuarios, // ← AGREGAR ESTA LÍNEA TAMBIÉN
                            noIngresadoAntes = true,
                            isLoading = false,  // ← Detener el loading
                            error = null        // ← Limpiar errores
                        )
                        RecuperarContrasena(correo)
                    }
                } else {
                    _uiState.value = uiState.value.copy(
                        autenticado = false,
                        isLoading = false,  // ← Detener el loading
                        error = "Credenciales inválidas"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = uiState.value.copy(
                    autenticado = false,
                    isLoading = false,  // ← Detener el loading
                    error = e.localizedMessage ?: "Error de autenticación"
                )
            }
        }
    }


    fun actualizarIDArbol(id: Int){
        _uiState.update { currentState->
            currentState.copy(id_Arbol = id)

        }
    }


    fun setServicio(id: Int){
        _uiState.update{currentState->
            currentState.copy(id_servicio=id)
        }
    }
    fun misArboles(id:Int){

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true) // Activar estado de carga
            try {
                val response=repository.misArboles(id)
                _uiState.value=_uiState.value.copy(
                    misArboles = response,
                    isLoading = false

                )

            }catch (e:Exception){
                _uiState.value = _uiState.value.copy(
                    error = "Error ${e.message}",
                    isLoading = false
                )
            }
        }
    }
    fun misArbolesInfo(id:Int){
        viewModelScope.launch {
            try {
                val response=repository.misArbolesInfo(id)
                _uiState.value=_uiState.value.copy(
                    arbolesInfo = response
                )
            }catch (e:Exception){
                _uiState.value=_uiState.value.copy(
                    error = "Error ${e.message}"
                )
            }
        }
    }
    fun obtenerServicios(){
        viewModelScope.launch {
            try {
                val response=repository.servicios()
                _uiState.value=_uiState.value.copy(
                    servicios = response
                )
            }catch (e:Exception){
                _uiState.value=_uiState.value.copy(
                    error = "Error ${e.message}"
                )
            }
        }
    }
    fun obtenerPerfil(){
        viewModelScope.launch {
            try {
                val response=repository.perfil(uiState.value.id_Us)
                _uiState.value=uiState.value.copy(
                    perfil = response
                )
            }catch (e:Exception){
                _uiState.value=_uiState.value.copy(
                    error = "Error ${e.message}"
                )
            }
        }
    }
    fun notificacionLeida(id: Int){
        viewModelScope.launch {
            try {
                val response=repository.notificacionLeida(id)
            }catch (e:Exception){
                _uiState.value=_uiState.value.copy(
                    error = "Error ${e.message}"
                )
            }
        }
    }
    fun historialServicios(){
        viewModelScope.launch {
            try {
                val response=repository.obtenerHistorialServicios(uiState.value.id_Us,uiState.value.id_Arbol)
                _uiState.value=_uiState.value.copy(
                    historialServicios = response
                )
            }catch (e:Exception){
                _uiState.value=_uiState.value.copy(
                    error="Error ${e.message}"
                )
            }
        }
    }

    fun agregarServicioDetalles(
        comentarios: String,
        altura: Float,
        circunferencia: Float,
        imagenBitmap: Bitmap?
    ) {
        val servicioId = uiState.value.id_servicio
        val idUsuario = uiState.value.id_Us
        val idArbol = uiState.value.id_Arbol

        viewModelScope.launch {
            try {
                if (servicioId == 4) {
                    // Validaciones
                    if (imagenBitmap == null) {
                        _uiState.value = _uiState.value.copy(error = "Debes tomar una foto del árbol.")
                        return@launch
                    }
                    if (altura <= 0f || circunferencia <= 0f) {
                        _uiState.value = _uiState.value.copy(error = "Altura y circunferencia deben ser mayores a 0.")
                        return@launch
                    }

                    // Convertir a Base64
                    val base64 = bitmapToBase64(imagenBitmap)

                    // Enviar a repositorio
                    repository.guardarDetallesMedicionServicio(
                        servicioId = servicioId,
                        comentarios = comentarios,
                        altura = altura,
                        circunferencia = circunferencia,
                        ID_US = idUsuario,
                        ID_ARBOL = idArbol,
                        bitmap = imagenBitmap // o base64 si es necesario
                    )

                    _uiState.value = _uiState.value.copy(error = null)

                } else {
                    // Servicio sin medición
                    repository.guardarDetallesServicio(
                        servicioId = servicioId,
                        comentarios = comentarios,
                        altura = altura,
                        circunferencia = circunferencia,
                        ID_US = idUsuario,
                        ID_ARBOL = idArbol
                    )
                    _uiState.value = _uiState.value.copy(error = null)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Error al guardar servicio: ${e.message}"
                )
            }
        }
    }


    fun notificacionesAlumno() {
        if(uiState.value.autenticado){
            viewModelScope.launch {
                while (true){
                    try{
                        val response=repository.getNotificaciones(uiState.value.id_Us)
                    }catch (e:Exception){
                        _uiState.value=_uiState.value.copy(
                            error="Error al actualizar: ${e.message}",

                            )
                        this@SiembraValoresViewModel.seguir =false
                    }
                }
            }
        }else{

        }

    }
    fun notificacionesPeriodicas() {
        viewModelScope.launch{
            while(seguir){
                try {
                    val response=repository.agregarNot()
                    delay(5*60*1000)
                }catch (e:Exception){
                    _uiState.value=_uiState.value.copy(
                        error="Error al actualizar: ${e.message}",

                        )
                    this@SiembraValoresViewModel.seguir =false
                }
            }
        }

    }
    fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP) // NO_WRAP evita problemas de salto de línea
    }

}