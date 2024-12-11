package com.example.siembravalores.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.siembravalores.data.Notificacion

@Composable
fun NotificacionesScreen(
    notificaciones: List<Notificacion>,
    onNotificationClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Notificaciones",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de notificaciones
        if (notificaciones.isEmpty()) {
            // Mostrar mensaje cuando no hay notificaciones
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay notificaciones",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        } else {
            // Lista de tarjetas de notificación
            LazyColumn(

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

@Composable
fun NotificationCard(
    notificacion: Notificacion,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono de notificación
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notification",
                modifier = Modifier
                    .size(40.dp)
                    .background(color = Color.White, shape = CircleShape)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Contenido de la notificación
            Column {
                Text(
                    text = notificacion.MENSAJE ?: "Sin mensaje",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = notificacion.FECHA_ENVIO ?: "Fecha no disponible",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

