package com.example.siembravalores.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import com.example.siembravalores.R
import com.example.siembravalores.data.SiembraValoresUiState

@Composable
fun PerfilScreen(
    consulta: () -> Unit,
    uiState: SiembraValoresUiState
) {
    LaunchedEffect(key1=true) {
        consulta()
    }

    // Extrae el perfil si está disponible
    val perfil = uiState.perfil.firstOrNull()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botón en la esquina superior derecha
//        IconButton(
//            onClick = onNextButtonClicked,
//            modifier = Modifier.align(Alignment.TopEnd)
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.misiones),
//                contentDescription = "Botón de Misiones"
//            )
//        }

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.perfil),

                contentDescription = "Imagen de perfil",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre completo
            Text(
                text = buildString {
                    append(perfil?.NOMBRE ?: "Nombre")
                    append(" ")
                    append(perfil?.AP_P ?: "")
                    append(" ")
                    append(perfil?.AP_M ?: "")
                }.trim(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // Correo
            Text(
                text = perfil?.CORREO ?: "correo@ejemplo.com",
                fontSize = 14.sp,
                color = Color.Blue
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Información de perfil con datos dinámicos
            ProfileInfo(
                label = "Fecha de nacimiento:",
                value = perfil?.FECHA_NAC ?: "No disponible"
            )
            ProfileInfo(
                label = "Fecha de registro:",
                value = perfil?.FECHA_REGISTRO ?: "No disponible"
            )
            ProfileInfo(
                label = "Celular:",
                value = perfil?.CELULAR ?: "No disponible"
            )
            ProfileInfo(
                label = "Escuela:",
                value = perfil?.ESCUELA ?: "No disponible"
            )

            Spacer(modifier = Modifier.weight(1f))

            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}

@Composable
fun ProfileInfo(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}