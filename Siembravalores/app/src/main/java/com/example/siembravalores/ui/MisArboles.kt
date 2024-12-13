package com.example.siembravalores.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.siembravalores.R
import com.example.siembravalores.data.SiembraValoresUiState
import com.example.siembravalores.data.infoArbol
import com.example.siembravalores.data.misArbolesData

@Composable
fun TreeList(
    consultaArboles: () -> Unit,
    consultaArbolesInfo: (Int) -> Unit,
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    uiState: SiembraValoresUiState
) {
    // Estado para manejar la expansión de las tarjetas
    var expandedCardIndex by remember { mutableStateOf(-1) }

    // Efecto para cargar los árboles al iniciar
    LaunchedEffect(key1 = true) {
        consultaArboles()
    }
    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White), // Fondo blanco
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else -> {
            LazyColumn(modifier = modifier.background(Color.White)) { // Fondo blanco
                itemsIndexed(uiState.misArboles) { index, tree ->
                    TreeItem(
                        tree = tree,
                        isExpanded = expandedCardIndex == index,
                        onExpandClick = {
                            // Alternar expansión de la tarjeta
                            expandedCardIndex = if (expandedCardIndex == index) -1 else index

                            // Cargar información específica del árbol cuando se expande
                            if (expandedCardIndex == index) {
                                consultaArbolesInfo(tree.ID_ARBOL)
                            }
                        },
                        arbolInfo = uiState.arbolesInfo.firstOrNull { it.ID_ARBOL == tree.ID_ARBOL },
                        onNextButtonClicked = onNextButtonClicked
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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
    arbolInfo: infoArbol?,
    onNextButtonClicked: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White), // Fondo blanco
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Imagen del árbol en la esquina superior izquierda
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Imagen del árbol",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 16.dp)
                )

                Column {
                    Text(
                        text = tree.NOMBRE_VALOR ?: "Árbol sin nombre",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black // Texto negro
                    )
                    Text(
                        text = tree.DESCRIPCION ?: "Sin descripción",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }

            // Botón de servicios bajo la imagen
            Button(
                onClick = { tree.ID_ARBOL?.let { onNextButtonClicked(it) } },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
                    .height(35.dp), // Tamaño reducido
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Servicio", color = Color.Black) // Texto del botón en negro
            }

            // Imagen expansora
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
                    .size(24.dp)
                    .clickable { onExpandClick() }
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(50))
            ) {
                Image(
                    painter = painterResource(if (isExpanded) R.drawable.expa else R.drawable.expa2),
                    contentDescription = "Expandir detalles",
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Expansor con información del árbol
            if (isExpanded) {
                arbolInfo?.let { info ->
                    Column(modifier = Modifier.padding(top = 8.dp)) {
                        Text(
                            text = "Nombre científico: ${info.NOMBRE_CIENTIFICO ?: "No disponible"}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black // Texto negro
                        )
                        Text(
                            text = "Fecha de plantación: ${info.FECHA_PLANTADO ?: "No disponible"}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black // Texto negro
                        )
                        Text(
                            text = "Descripción: ${info.DESCRIPCION ?: "No disponible"}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black // Texto negro
                        )
                        Text(
                            text = "Endémico: ${if (info.ENDEMICO) "Sí" else "No"}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black // Texto negro
                        )
                        Text(
                            text = "Altura: ${info.ALTURA ?: "No disponible"}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black // Texto negro
                        )
                        Text(
                            text = "Circunferencia: ${info.CIRCUNFERENCIA ?: "No disponible"}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black // Texto negro
                        )
                        Text(
                            text = "Valor: ${info.VALOR ?: "No disponible"}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black // Texto negro
                        )
                        Text(
                            text = "Colonia: ${info.COLONIA ?: "No disponible"}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black // Texto negro
                        )
                    }
                }
            }
        }
    }
}





