package com.example.siembravalores.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import com.example.siembravalores.R
import com.example.siembravalores.data.SiembraValoresUiState
import com.example.siembravalores.ui.theme.TreeColors

@Composable
fun PerfilScreen(
    consulta: () -> Unit,
    uiState: SiembraValoresUiState
) {
    LaunchedEffect(key1 = true) {
        consulta()
    }

    // Extrae el perfil si está disponible
    val perfil = uiState.perfil.firstOrNull()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        TreeColors.SkyBlue.copy(alpha = 0.3f),
                        TreeColors.LightGreen.copy(alpha = 0.2f),
                        TreeColors.PureWhite
                    )
                )
            )
            .padding(16.dp)
    ) {
        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título divertido
            Text(
                text = "🌱 Mi Perfil de Guardián de Árboles 🌱",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TreeColors.DarkGreen,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Tarjeta de perfil con sombra
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(20.dp)
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = TreeColors.PureWhite
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Imagen de perfil con borde verde
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        TreeColors.LeafGreen,
                                        TreeColors.MediumGreen
                                    )
                                ),
                                shape = CircleShape
                            )
                            .padding(4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.perfil),
                            contentDescription = "Mi foto de perfil",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(TreeColors.PureWhite),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Nombre completo con decoración
                    Text(
                        text = "🌟 ${buildString {
                            append(perfil?.NOMBRE ?: "Pequeño Guardián")
                            append(" ")
                            append(perfil?.AP_P ?: "")
                            append(" ")
                            append(perfil?.AP_M ?: "")
                        }.trim()} 🌟",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TreeColors.DarkGreen
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Correo con icono
                    Text(
                        text = "📧 ${perfil?.CORREO ?: "correo@ejemplo.com"}",
                        fontSize = 14.sp,
                        color = TreeColors.MediumGreen,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Información de perfil con tarjetas individuales
                    ProfileInfoCard(
                        icon = "🎂",
                        label = "Mi cumpleaños",
                        value = perfil?.FECHA_NAC ?: "No disponible"
                    )

                    ProfileInfoCard(
                        icon = "📅",
                        label = "Miembro desde",
                        value = perfil?.FECHA_REGISTRO ?: "No disponible"
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            }
        }
    }


@Composable
fun ProfileInfoCard(icon: String, label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = TreeColors.SoftGray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = icon,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = label,
                    fontWeight = FontWeight.Bold,
                    color = TreeColors.DarkGreen,
                    fontSize = 12.sp
                )
            }
            Text(
                text = value,
                color = TreeColors.MediumGreen,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )
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
        Text(text = label, fontWeight = FontWeight.Bold, color = TreeColors.DarkGreen)
        Text(text = value, color = TreeColors.MediumGreen)
    }
}