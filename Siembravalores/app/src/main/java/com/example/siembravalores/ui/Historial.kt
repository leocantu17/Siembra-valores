package com.example.siembravalores.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.siembravalores.R
import com.example.siembravalores.data.HistorialServicios
import com.example.siembravalores.data.SiembraValoresUiState

@Composable
fun HistorialServiciosCard(
    historialServicio: HistorialServicios,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth().padding(8.dp)) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ServiceTypeIcon(
                serviceType = historialServicio.NOMBRE_SERVICIO ?: "N/A",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )
            Column {

                Text(
                    text = "Fecha: ${historialServicio.FECHA_SERVICIO ?: "N/A"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Comentarios: ${historialServicio.COMENTARIOS ?: "N/A"}",
                    style = MaterialTheme.typography.bodyMedium
                )
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
        else -> R.drawable.logo // Un ícono predeterminado si no coincide
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
    LazyColumn(modifier = modifier) {
        items(historialServicios) { servicio ->
            HistorialServiciosCard(
                historialServicio = servicio,
                modifier = Modifier.padding(8.dp)
            )
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

    Surface(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        HistorialServiciosList(historialServicios = uiState.historialServicios)
    }
}