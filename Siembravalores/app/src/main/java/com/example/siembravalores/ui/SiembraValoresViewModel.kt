package com.example.siembravalores.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.siembravalores.data.Notificacion
import com.example.siembravalores.data.SiembraValoresUiState
import com.example.siembravalores.repository.Repositorio
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SiembraValoresViewModel(private val repository:Repositorio=Repositorio()):ViewModel() {
    private val _uiState= MutableStateFlow(SiembraValoresUiState())
    val uiState: StateFlow<SiembraValoresUiState> =_uiState.asStateFlow()

    var correo by mutableStateOf("")
        private set
    var contrasena by mutableStateOf("")
        private set
    var nombreArbol by mutableStateOf("")
        private set
    var seguir by mutableStateOf(true)
        private set

    fun updateCorreo(correoUsuario:String){
        correo=correoUsuario
        _uiState.update { currentState->
            currentState.copy(correo=correoUsuario)
        }
    }
    init{
        notificacionesPeriodicas()
        notificacionesAlumno()
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

    fun updateContrasena(contrasenaUsuario:String){
        contrasena=contrasenaUsuario
        _uiState.update { currentState->
            currentState.copy(contrasena=contrasenaUsuario)
        }
    }

    fun updateNombreArbol(nombre:String){
        nombreArbol=nombre
        _uiState.update { currentState->
            currentState.copy(nombreArbol = nombre)
        }
    }
    fun autenticarUsuario(correo: String, contrasena: String) {
        viewModelScope.launch {
            try {
                val usuarios = repository.obtenerUsuarios(correo, contrasena)

                if (usuarios.isNotEmpty()) {
                    val primerId = usuarios.first().id
                    _uiState.value = uiState.value.copy(
                            usuario = usuarios,
                            id_Us = primerId,
                            autenticado = true,
                            error = "Encontre algo"
                    )
                } else {
                    _uiState.value = uiState.value.copy(
                            autenticado = false,
                    error = "Credenciales inválidas"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = uiState.value.copy(
                        autenticado = false,
                        error = e.localizedMessage ?: "Error de autenticación"
                )
            }
        }
    }
    fun valores(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true) // Activar estado de carga
            try{
                val valores=repository.obtenerValores()
                _uiState.value=uiState.value.copy(
                    isLoading = false,
                    valores = valores
                )
            }catch (e: Exception) {
                // En caso de error, actualizar la UI con el mensaje de error
                _uiState.value = _uiState.value.copy(
                    isLoading = false, // Desactivar estado de carga
                    error = "Error al cargar los valores: ${e.message}"
                )
            }
        }
    }
    fun arbolesDisponibles() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true) // Activar estado de carga

            try {
                val response = repository.obtenerArboles(uiState.value.id_Us)
                if(response.isEmpty()){
                    _uiState.value=_uiState.value.copy(
                        error = "No hay árboles disponibles en tu zona",
                        isLoading = false
                    )
                }else{
                    // Si la respuesta es exitosa, actualizar la UI con los datos
                    _uiState.value = _uiState.value.copy(
                        arboles = response,
                        isLoading = false, // Desactivar estado de carga
                        error = "" // Limpiar el mensaje de error
                    )
                }

            } catch (e: Exception) {
                // En caso de error, actualizar la UI con el mensaje de error
                _uiState.value = _uiState.value.copy(
                    isLoading = false, // Desactivar estado de carga
                    error = "Error al cargar los árboles: ${e.message}"
                )
            }
        }
    }
    fun actualizarIDArbol(id: Int){
        _uiState.update { currentState->
            currentState.copy(id_Arbol = id)

        }
    }
    fun InformacionArbol(){
        viewModelScope.launch {
            try {
                val response=repository.obtenerInfoArbol(uiState.value.id_Arbol)
                _uiState.value=_uiState.value.copy(
                    arboles = response,
                    error = ""
                )
            }catch (e:Exception){
                _uiState.value=_uiState.value.copy(
                    error = "Error ${e.message}"
                )
            }
        }
    }
    fun AdoptarArbol() {
        viewModelScope.launch {
            try {
                // Obtener el ID_VALOR del primer árbol (o del árbol seleccionado)
                val idValor = uiState.value.arboles.mapNotNull { it.ID_VALOR }.firstOrNull()

                // Usar idValor si existe
                idValor?.let { valor ->
                    val repository = repository.adoptarArbol(
                        uiState.value.id_Us,
                        uiState.value.id_Arbol,
                        valor  // Pasar el ID_VALOR al método de adopción
                    )
                } ?: run {
                    // Manejar el caso cuando no hay ID_VALOR
                    _uiState.value = _uiState.value.copy(
                        error = "No se encontró un valor de árbol válido"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Error ${e.message}"
                )
            }
        }
    }
     fun upadteArbolValue(id:String,name:String){
         _uiState.update { currentState ->
             val updatedArboles = currentState.arboles.map { arbol ->
                 // Update the first (or only) tree's value
                 if (arbol == currentState.arboles.first()) {
                     arbol.copy(
                         ID_VALOR = id,
                         NOMBRE_VALOR = name
                     )
                 } else {
                     arbol
                 }
             }

             currentState.copy(arboles = updatedArboles)
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
    fun setServicio(id: Int){
        _uiState.update{currentState->
            currentState.copy(ID_SERVICIO=id)
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
}