package com.example.siembravalores.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.siembravalores.data.SiembraValoresUiState

@Composable
fun AddServiceScreen(
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    consulta: () -> Unit,
    uiState: SiembraValoresUiState,
    onSelectionChange: (Int) -> Unit,
    onServiceDetailsSubmit: (String, Float, Float) -> Unit
) {
    // Launch the initial query when the composable is first created
    LaunchedEffect(key1 = true) {
        consulta()
    }

    // State variables for input fields
    var comments by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var circumference by remember { mutableStateOf("") }
    var selectedServicioId by remember { mutableStateOf<Int?>(null) }

    // Scroll state
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(scrollState) // Add vertical scrolling
    ) {
        Text(
            text = "Selecciona un Servicio",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Render service selection options
        uiState.servicios.forEach { servicio ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedServicioId == servicio.ID_SERVICIO,
                        onClick = {
                            selectedServicioId = servicio.ID_SERVICIO
                            onSelectionChange(servicio.ID_SERVICIO)
                            println("Servicio seleccionado: ${servicio.TIPO}")
                        }
                    )
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedServicioId == servicio.ID_SERVICIO,
                    onClick = {
                        selectedServicioId = servicio.ID_SERVICIO
                        onSelectionChange(servicio.ID_SERVICIO)
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = servicio.TIPO ?: "Servicio sin nombre",
                    color = Color.Black
                )
            }
        }

        // Comments section
        Text(
            text = "Comentarios",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        BasicTextField(
            value = comments,
            onValueChange = { comments = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
                .background(color = Color.LightGray),
            keyboardOptions = KeyboardOptions.Default,
            textStyle = LocalTextStyle.current.copy(color = Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Conditional rendering for specific service (ID 9)
        if (uiState.ID_SERVICIO == 4) {
            // Height input
            TextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Altura", color = Color.Black) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Circumference input
            TextField(
                value = circumference,
                onValueChange = { circumference = it },
                label = { Text("Circunferencia", color = Color.Black) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Submit button
        Button(
            onClick = {
                // Enviar los detalles usando la nueva lambda
                onServiceDetailsSubmit(
                    comments,
                    height.toFloatOrNull() ?: 0f,
                    circumference.toFloatOrNull() ?: 0f
                )
                onNextButtonClicked()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Agregar", color = Color.Black)
        }
    }
}