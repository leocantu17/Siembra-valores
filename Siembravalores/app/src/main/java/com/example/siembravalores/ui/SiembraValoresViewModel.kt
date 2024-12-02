package com.example.siembravalores.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.siembravalores.data.SiembraValoresUiState
import com.example.siembravalores.repository.Repositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SiembraValoresViewModel(private val repository:Repositorio=Repositorio()):ViewModel() {
    private val _uiState= MutableStateFlow(SiembraValoresUiState())
    val uiState: StateFlow<SiembraValoresUiState> =_uiState.asStateFlow()

    var correo by mutableStateOf("")
        private set
    var contrasena by mutableStateOf("")
        private set

    fun updateCorreo(correoUsuario:String){
        correo=correoUsuario
    }

    fun updateContrasena(contrasenaUsuario:String){
        contrasena=contrasenaUsuario
    }
    fun autenticarUsuario(correo: String, contrasena: String) {
        viewModelScope.launch {
            try {
                val usuarios = repository.obtenerUsuarios(correo, contrasena)
                if (usuarios.isNotEmpty()) {
                    _uiState.value = uiState.value.copy(
                            usuario = usuarios,
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
}