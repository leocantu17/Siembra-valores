//package com.example.siembravalores.ui
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.siembravalores.R
//import com.example.siembravalores.data.Arboles
//import com.example.siembravalores.data.SiembraValoresUiState
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PantallaAdopcion(
//    modifier: Modifier = Modifier,
//    onNextButtonClicked: () -> Unit,
//    consulta: () -> Unit,
//    InformacionArbol: List<Arboles>,
//    onValueChange: (String, String) -> Unit, // Modified to pass both ID and Name
//    uiState: SiembraValoresUiState
//) {
//    // Get the first tree from the list (or null if empty)
//    val arbol = InformacionArbol.firstOrNull()
//
//    // Trigger initial data fetch when the composable is first launched
//    LaunchedEffect(Unit) {
//        consulta()
//    }
//
//    // Different UI states handling
//    when {
//        // Loading state
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
//
//        // Error state
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
//
//        // No trees available
//        arbol == null -> {
//            Text(
//                text = "No hay árboles disponibles",
//                color = Color.Black, // Texto negro
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//
//        // Main content when a tree is available
//        else -> {
//            Column(
//                modifier = modifier
//                    .fillMaxSize()
//                    .background(Color.White) // Fondo blanco
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.Start
//            ) {
//                // Tree image and information row
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    // Tree/App Logo Image
//                    Image(
//                        painter = painterResource(id = R.drawable.logo),
//                        contentDescription = "Árbol",
//                        modifier = Modifier
//                            .size(150.dp)
//                            .align(Alignment.CenterVertically)
//                    )
//
//                    Spacer(modifier = Modifier.width(16.dp))
//
//                    // Tree name and description column
//                    Column(
//                        verticalArrangement = Arrangement.spacedBy(4.dp),
//                        horizontalAlignment = Alignment.Start
//                    ) {
//                        arbol.NOMBRE?.let {
//                            Text(
//                                text = it,
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.Black // Texto negro
//                            )
//                        }
//                        arbol.DESCRIPCION?.let {
//                            Text(
//                                text = it,
//                                fontSize = 14.sp,
//                                color = Color.Gray
//                            )
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // State for dropdown selection
//                val (selectedOption, setSelectedOption) = remember {
//                    mutableStateOf(arbol.NOMBRE_VALOR ?: "")
//                }
//                val (isDropdownExpanded, setIsDropdownExpanded) = remember {
//                    mutableStateOf(false)
//                }
//
//                // Value selection dropdown
//                if (arbol.ID_VALOR == null) {
//                    Column {
//                        ExposedDropdownMenuBox(
//                            expanded = isDropdownExpanded,
//                            onExpandedChange = { setIsDropdownExpanded(!isDropdownExpanded) }
//                        ) {
//                            TextField(
//                                value = selectedOption.ifEmpty { "Selecciona valor" },
//                                onValueChange = { },
//                                label = { Text("Valores", color = Color.Black) }, // Texto negro
//                                readOnly = true,
//                                trailingIcon = {
//                                    ExposedDropdownMenuDefaults.TrailingIcon(
//                                        expanded = isDropdownExpanded
//                                    )
//                                },
//                                colors = ExposedDropdownMenuDefaults.textFieldColors(),
//                                modifier = Modifier.menuAnchor()
//                            )
//
//                            ExposedDropdownMenu(
//                                expanded = isDropdownExpanded,
//                                onDismissRequest = { setIsDropdownExpanded(false) }
//                            ) {
//                                uiState.valores.forEach { valor ->
//                                    DropdownMenuItem(
//                                        text = { Text(valor.VALOR ?: "Sin nombre", color = Color.Black) }, // Texto negro
//                                        onClick = {
//                                            // Update selected option
//                                            setSelectedOption(valor.VALOR ?: "")
//
//                                            // Close dropdown
//                                            setIsDropdownExpanded(false)
//
//                                            // Call onValueChange with both ID and Name
//                                            onValueChange(
//                                                valor.ID_VALOR ?: "",
//                                                valor.VALOR ?: ""
//                                            )
//                                        },
//                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
//                                    )
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    // If a value is already selected, show it
//                    Text(
//                        text = "Valor: ${arbol.NOMBRE_VALOR}",
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black // Texto negro
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Tree details
//                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                    Text(
//                        text = "Fecha de plantado: ${arbol.FECHA_PLANTADO ?: "No disponible"}",
//                        fontSize = 14.sp,
//                        color = Color.Black // Texto negro
//                    )
//                    Text(
//                        text = "Colonia: ${arbol.COLONIA ?: "No especificada"}",
//                        fontSize = 14.sp,
//                        color = Color.Black // Texto negro
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Adopt button
//                Button(
//                    onClick = onNextButtonClicked,
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                ) {
//                    Text("Adoptar", color = Color.Black) // Texto negro
//                }
//            }
//        }
//    }
//}
