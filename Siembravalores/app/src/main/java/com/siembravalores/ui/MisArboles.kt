package com.siembravalores.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.siembravalores.data.SiembraValoresUiState
import com.siembravalores.data.infoArbol
import com.siembravalores.data.misArbolesData
import com.siembravalores.R

// Beautiful green color palette (same as other screens)
val LightGreen = Color(0xFF81C784)
val MediumGreen = Color(0xFF66BB6A)
val DarkGreen = Color(0xFF4CAF50)
val VeryLightGreen = Color(0xFFE8F5E8)
val AccentGreen = Color(0xFF2E7D32)
val SoftWhite = Color(0xFFFAFAFA)
val TreeBrown = Color(0xFF8D6E63)
val SkyBlue = Color(0xFF87CEEB)

@Composable
fun TreeList(
    consultaArboles: () -> Unit,
    consultaArbolesInfo: (Int) -> Unit,
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    uiState: SiembraValoresUiState,
    onNextButtonHistorial: (Int) -> Unit
) {
    // Estado para manejar la expansión de las tarjetas
    var expandedCardIndex by remember { mutableStateOf(-1) }

    // Animación para elementos flotantes
    val infiniteTransition = rememberInfiniteTransition()
    val floatAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Efecto para cargar los árboles al iniciar
    LaunchedEffect(key1 = true) {
        consultaArboles()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        SkyBlue.copy(alpha = 0.2f),
                        VeryLightGreen,
                        LightGreen.copy(alpha = 0.1f),
                        SoftWhite
                    )
                )
            )
    ) {
        // Elementos decorativos flotantes
        Text(
            text = "🌿",
            fontSize = 28.sp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 32.dp)
                .rotate(floatAnimation)
        )

        Text(
            text = "🐛",
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 32.dp, top = 64.dp)
                .scale(1f + floatAnimation * 0.02f)
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = DarkGreen,
                                strokeWidth = 4.dp,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "🌱 Cargando tus árboles...",
                                style = MaterialTheme.typography.titleMedium,
                                color = MediumGreen,
                                fontWeight = FontWeight.Medium
                            )

                    }
                }
            }
            uiState.misArboles.isEmpty() -> {
                // Estado vacío
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                        Column(
                            modifier = Modifier.padding(40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "🌳",
                                fontSize = 64.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(
                                text = "¡Aún no tienes árboles!",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = DarkGreen,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "¡Pronto podrás adoptar tu primer árbol y ayudar al planeta! 🌍",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MediumGreen,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }

            }
            else -> {
                Column {
                    // Encabezado bonito
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🌳",
                                fontSize = 32.sp,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Column {
                                Text(
                                    text = "Mis Árboles Especiales",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkGreen
                                )
                                Text(
                                    text = "Tienes ${uiState.misArboles.size} árboles creciendo 🌱",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MediumGreen,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                    }

                    LazyColumn(
                    ) {
                        itemsIndexed(uiState.misArboles) { index, tree ->
                            TreeItem(
                                tree = tree,
                                isExpanded = expandedCardIndex == index,
                                onExpandClick = {
                                    expandedCardIndex = if (expandedCardIndex == index) -1 else index
                                    if (expandedCardIndex == index) {
                                        consultaArbolesInfo(tree.ID_ARBOL)
                                    }
                                },
                                uiState = uiState,
                                onNextButtonHistorial = onNextButtonHistorial,
                                arbolInfo = uiState.arbolesInfo.firstOrNull { it.ID_ARBOL == tree.ID_ARBOL },
                                onNextButtonClicked = onNextButtonClicked
                            )
                        }

                        // Espaciado final
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TreeItem(
    tree: misArbolesData,
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
    uiState: SiembraValoresUiState,
    arbolInfo: infoArbol?,
    onNextButtonClicked: (Int) -> Unit,
    onNextButtonHistorial: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Información principal del árbol
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Imagen del árbol con marco bonito
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        VeryLightGreen,
                                        LightGreen.copy(alpha = 0.5f)
                                    )
                                )
                            )
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Mi árbol especial",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = tree.VALOR_ASOCIADO ?: "Mi Árbol Especial",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen
                        )
                        Text(
                            text = tree.DESCRIPCION ?: "Un árbol lleno de valores",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MediumGreen,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Botón expandir
                IconButton(
                    onClick = onExpandClick,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(LightGreen.copy(alpha = 0.3f))
                ) {
                    Image(
                        painter = painterResource(if (isExpanded) R.drawable.expa else R.drawable.expa2),
                        contentDescription = "Expandir detalles",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { tree.ID_ARBOL?.let { onNextButtonClicked(it) } },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {

                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Servicios",
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = { tree.ID_ARBOL?.let { onNextButtonHistorial(it) } },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MediumGreen,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Historial",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Información expandida con animación
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                arbolInfo?.let { info ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = VeryLightGreen),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "🔬 Detalles de mi árbol",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = DarkGreen,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            TreeDetailRow("🌿 Nombre científico", info.NOMBRE_CIENTIFICO ?: "Por descubrir")
                            TreeDetailRow("📅 Fecha de plantación", info.FECHA_PLANTADO ?: "Fecha especial")
                            TreeDetailRow("🏞️ Endémico", if (info.ENDEMICO) "¡Sí, muy especial!" else "No, pero igual de importante")
                            TreeDetailRow("💎 Valor", info.VALOR_ASOCIADO ?: "Valor único")
                            TreeDetailRow("🏘️ Colonia", info.COLONIA ?: "Lugar especial")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TreeDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = AccentGreen,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = DarkGreen,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}