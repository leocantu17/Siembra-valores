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
    val serviceOptions = listOf("Podar", "Regar", "Fumigar", "Fertilizar", "Medir")
    var selectedOption by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Tipo de servicio", fontSize = 16.sp)
        serviceOptions.forEachIndexed { index, service ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedOption == index,
                    onClick = { selectedOption = index },
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = service, fontSize = 14.sp)
            }
        }

        Text(
            text = "Comentarios",
            fontSize = 16.sp
        )
        BasicTextField(
            value = comments,
            onValueChange = { comments = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                .padding(4.dp),
            keyboardOptions = KeyboardOptions.Default
        )

        TextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Altura") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
        )

        TextField(
            value = circumference,
            onValueChange = { circumference = it },
            label = { Text("Circunferencia") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
        )

        Button(
            onClick = onNextButtonClicked,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Agregar", fontSize = 14.sp)
        }
    }
}
