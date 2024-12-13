package com.example.siembravalores.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
    modifier: Modifier,
    consulta: () -> Unit,
    uiState: SiembraValoresUiState,
    onSelectionChange: (Int) -> Unit,
    onServiceDetailsSubmit: (Int, String, Float, Float) -> Unit
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
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Text(
            text = "Selecciona un Servicio",
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 16.sp
        )

        uiState.servicios.forEach { servicio ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedServicioId == servicio.ID_SERVICIO,
                        onClick = {
                            selectedServicioId = servicio.ID_SERVICIO
                            onSelectionChange(servicio.ID_SERVICIO)
                        }
                    )
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedServicioId == servicio.ID_SERVICIO,
                    onClick = {
                        selectedServicioId = servicio.ID_SERVICIO
                        onSelectionChange(servicio.ID_SERVICIO)
                    }
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = servicio.TIPO ?: "Servicio sin nombre",
                    color = Color.Black,
                    fontSize = 12.sp
                )
            }
        }

        Text(
            text = "Comentarios",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        BasicTextField(
            value = comments,
            onValueChange = { comments = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(4.dp)
                .background(color = Color.LightGray),
            keyboardOptions = KeyboardOptions.Default,
            textStyle = LocalTextStyle.current.copy(color = Color.Black)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.ID_SERVICIO == 9) {
            TextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Altura", color = Color.Black, fontSize = 12.sp) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 12.sp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = circumference,
                onValueChange = { circumference = it },
                label = { Text("Circunferencia", color = Color.Black, fontSize = 12.sp) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 12.sp)
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
            onClick = {
                onServiceDetailsSubmit(
                    selectedServicioId ?: 0,
                    comments,
                    height.toFloatOrNull() ?: 0f,
                    circumference.toFloatOrNull() ?: 0f
                )
                onNextButtonClicked()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Agregar", color = Color.Black, fontSize = 14.sp)
        }
    }
}
