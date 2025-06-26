package com.siembravalores.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.siembravalores.data.HistorialServicios
import com.siembravalores.data.SiembraValoresUiState
import com.example.siembravalores.ui.theme.TreeColors
import com.siembravalores.R

@Composable
fun HistorialServiciosCard(
    historialServicio: HistorialServicios,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = TreeColors.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon container with background
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(TreeColors.VeryLightGreen),
                contentAlignment = Alignment.Center
            ) {
                ServiceTypeIcon(
                    serviceType = historialServicio.NOMBRE_SERVICIO ?: "N/A",
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Service name with emoji
                Text(
                    text = "${getServiceEmoji(historialServicio.NOMBRE_SERVICIO)} ${historialServicio.NOMBRE_SERVICIO ?: "Servicio especial"}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TreeColors.DarkText,
                        fontSize = 16.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Date with calendar emoji
                Text(
                    text = "📅 ${historialServicio.FECHA_SERVICIO ?: "Fecha no disponible"}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TreeColors.DarkText,
                        fontSize = 14.sp
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Comments with chat emoji
                val comentarios = historialServicio.COMENTARIOS
                if (!comentarios.isNullOrBlank()) {
                    Text(
                        text = "💬 $comentarios",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TreeColors.DarkText.copy(alpha = 0.8f),
                            fontSize = 13.sp
                        ),
                        maxLines = 2
                    )
                } else {
                    Text(
                        text = "💬 ¡Tu árbol creció un poquito más!",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TreeColors.DarkText.copy(alpha = 0.6f),
                            fontSize = 13.sp,
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ServiceTypeIcon(
    serviceType: String,
    modifier: Modifier = Modifier
) {
    val imageRes = when (serviceType.lowercase()) {
        "poda" -> R.drawable.podar
        "fumigación" -> R.drawable.fumigar
        "riego" -> R.drawable.regar
        "medición" -> R.drawable.medir
        "fertilización" -> R.drawable.fertilizar
        else -> R.drawable.logo // Default icon if no match
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = serviceType,
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}

@Composable
fun HistorialServiciosList(
    historialServicios: List<HistorialServicios>,
    modifier: Modifier = Modifier
) {
    if (historialServicios.isEmpty()) {
        // Empty state with encouraging message
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                colors = CardDefaults.cardColors(containerColor = TreeColors.VeryLightGreen),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🌱",
                        fontSize = 48.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "¡Tu aventura está comenzando!",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TreeColors.DarkText
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Cuando cuides tu árbol, aquí aparecerá todo lo que has hecho por él.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TreeColors.DarkText.copy(alpha = 0.8f)
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(historialServicios) { servicio ->
                HistorialServiciosCard(
                    historialServicio = servicio
                )
            }

            // Add some bottom padding for better scrolling
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun HistorialServiciosScreen(
    uiState: SiembraValoresUiState,
    onConsulta: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = true) {
        onConsulta()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        TreeColors.BackgroundGreen,
                        TreeColors.VeryLightGreen
                    )
                )
            )
            .statusBarsPadding()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        // Header with fun title
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = TreeColors.MediumGreen),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "🌳 Historia de mi Árbol 📖",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TreeColors.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
        }

        // Services list
        HistorialServiciosList(
            historialServicios = uiState.historialServicios,
            modifier = Modifier.weight(1f)
        )
    }
}

// Helper function to get appropriate emoji for each service type
private fun getServiceEmoji(serviceType: String?): String {
    return when (serviceType?.lowercase()) {
        "poda" -> "✂️"
        "fumigación" -> "🚿"
        "riego" -> "💧"
        "medición" -> "📏"
        "fertilización" -> "🌱"
        else -> "🌿"
    }
}