package com.example.siembravalores.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import com.example.siembravalores.data.Notificacion
import com.example.siembravalores.data.SiembraValoresUiState
import com.example.siembravalores.ui.theme.TreeColors

@Composable
fun NotificacionesScreen(
    notificaciones: List<Notificacion>,
    onNotificationClick: (Int) -> Unit,
    uiState: SiembraValoresUiState
) {
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Encabezado con título divertido
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = TreeColors.LeafGreen.copy(alpha = 0.3f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🔔",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Mensajes de mis Árboles",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TreeColors.DarkGreen
                    )
                    Text(
                        text = "🌳",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            // Lista de notificaciones
            if (notificaciones.isEmpty()) {
                // Mensaje cuando no hay notificaciones con diseño amigable
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = TreeColors.PureWhite
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🌱",
                            fontSize = 60.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = "¡Qué tranquilo está todo!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TreeColors.DarkGreen,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tus árboles están creciendo felices.\nTe avisaremos cuando necesiten algo.",
                            fontSize = 14.sp,
                            color = TreeColors.MediumGreen,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(3) {
                                Text(
                                    text = "🌿",
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        }
                    }
                }
            } else {
                // Lista de tarjetas de notificación
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(notificaciones) { notificacion ->
                        NotificationCard(
                            notificacion = notificacion,
                            onClick = { onNotificationClick(notificacion.ID_NOTIFIACION) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationCard(
    notificacion: Notificacion,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = TreeColors.PureWhite
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono de notificación con fondo verde decorativo
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                TreeColors.LeafGreen.copy(alpha = 0.8f),
                                TreeColors.MediumGreen.copy(alpha = 0.6f)
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificación del árbol",
                    modifier = Modifier.size(28.dp),
                    tint = TreeColors.PureWhite
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Contenido de la notificación
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "🌳 ${notificacion.MENSAJE ?: "Tu árbol tiene algo que decirte"}",
                    fontSize = 16.sp,
                    color = TreeColors.DarkGreen,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📅",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = notificacion.FECHA_ENVIO ?: "Fecha no disponible",
                        fontSize = 12.sp,
                        color = TreeColors.MediumGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Indicador visual de nueva notificación
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        color = TreeColors.SunYellow,
                        shape = CircleShape
                    )
            )
        }
    }
}