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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Color

@Composable
fun AddServiceScreen(onNextButtonClicked: () -> Unit) {
    var comments by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var circumference by remember { mutableStateOf("") }
    val serviceOptions = listOf("Podar", "Regar", "Fumigar", "Fertilizar", "Medir")
    val checkedState = remember { mutableStateListOf(false, false, false, false, false) }

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                IconButton(onClick = { /* Acción para regresar */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Agregar servicio", style = MaterialTheme.typography.titleLarge)
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Tipo de servicio", fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
                serviceOptions.forEachIndexed { index, service ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Checkbox(
                            checked = checkedState[index],
                            onCheckedChange = { checkedState[index] = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = service)
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
                        .background(Color.LightGray) // Color de fondo para el TextField
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions.Default
                )

                Spacer(modifier = Modifier.height(16.dp))
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
            }
        }
    )
}
