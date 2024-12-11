package com.example.siembravalores.ui


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.ui.graphics.Color
import com.example.siembravalores.data.SiembraValoresUiState

@Composable
fun AddServiceScreen(
    onNextButtonClicked: () -> Unit,
    modifier: Modifier,
    consulta: () -> Unit,
    uiState: SiembraValoresUiState,
    onSelectionChange: (Int) -> Unit
) {
    LaunchedEffect(key1 = true) {
        consulta()
    }
    var comments by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var circumference by remember { mutableStateOf("") }
    var selectedServicioId by remember { mutableStateOf<Int?>(null) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Selecciona un Servicio",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        uiState.servicios.forEach { servicio ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedServicioId == servicio.ID_SERVICIO,
                        onClick = {
                            // Actualizar el ID de servicio seleccionado
                            selectedServicioId = servicio.ID_SERVICIO
                            onSelectionChange(servicio.ID_SERVICIO)
                            // Puedes agregar aquí cualquier lógica adicional
                            // cuando se selecciona un servicio
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
                )
            }
        }
        Text(
            text = "Comentarios",
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        BasicTextField(
            value = comments,
            onValueChange = { comments = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
                .background(color=Color.LightGray)
            ,
            keyboardOptions = KeyboardOptions.Default
        )

        Spacer(modifier = Modifier.height(16.dp))
        if (uiState.ID_SERVICIO == 9) {
            TextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Altura") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = circumference,
                onValueChange = { circumference = it },
                label = { Text("Circunferencia") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onNextButtonClicked,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Agregar")
            }

        } else {
            print("Hola")
        }

    }
}


