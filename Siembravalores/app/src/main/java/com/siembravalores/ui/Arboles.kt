//package com.example.siembravalores.ui
//
//import android.util.Log
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.siembravalores.R
//import com.example.siembravalores.data.Arboles
//import com.example.siembravalores.data.SiembraValoresUiState
//
//@Composable
//fun Arboles(
//    onNextButtonClicked: (Int) -> Unit,
//    consulta: () -> Unit,
//    uiState: SiembraValoresUiState
//) {
//    // Previous implementation remains the same
//    LaunchedEffect(key1 = true) {
//        consulta()
//    }
//
//    when {
//        uiState.isLoading -> {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White), // Fondo blanco
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator()
//            }
//        }
//        uiState.error.isNotEmpty() -> {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White), // Fondo blanco
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "Error: ${uiState.error}",
//                    color = Color.Red,
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
//        }
//        else -> {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White) // Fondo blanco
//            ) {
//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(2),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    items(uiState.arboles) { arbol ->
//                        ArbolGridItem(
//                            arbol = arbol,
//                            onNextButtonClicked = onNextButtonClicked
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ArbolGridItem(
//    arbol: Arboles,
//    onNextButtonClicked: (Int) -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(4.dp)
//            .background(Color.White), // Fondo blanco
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(8.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.logo),
//                contentDescription = "Imagen del Árbol",
//                modifier = Modifier.size(60.dp)
//            )
//            Text(
//                text = arbol.NOMBRE ?: "Sin nombre",
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.Black, // Texto negro
//                modifier = Modifier.padding(top = 8.dp)
//            )
//
//            Text(
//                text = arbol.DESCRIPCION ?: "Sin descripción disponible",
//                fontSize = 12.sp,
//                textAlign = TextAlign.Center,
//                color = Color.Black, // Texto negro
//                modifier = Modifier
//                    .padding(vertical = 8.dp)
//                    .fillMaxWidth()
//            )
//
//            Button(
//                onClick = {
//                    arbol.ID?.let { onNextButtonClicked(it) }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Seleccionar", color = Color.Black) // Texto negro
//            }
//        }
//    }
//}

