package com.example.siembravalores.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.siembravalores.R

@Composable
fun PantallaAdopcion(modifier: Modifier = Modifier, onNextButtonClicked: () -> Unit) {
    var treeName = TextFieldValue("")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Botón de regresar
        IconButton(onClick = { /* Acción para regresar */ }) {
            Icon(
                painter = painterResource(id = R.drawable.logo), // Asegúrate de incluir un ícono llamado "ic_back" en tu carpeta drawable
                contentDescription = "Regresar"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Imagen y nombre científico/descripción al lado
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del árbol
            Image(
                painter = painterResource(id = R.drawable.logo), // Asegúrate de incluir una imagen llamada "ic_tree" en drawable
                contentDescription = "Árbol",
                modifier = Modifier
                    .size(150.dp) // Tamaño de la imagen
                    .align(Alignment.CenterVertically) // Alineación vertical
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Nombre científico y descripción
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Nombre científico: Dalbergia retusa",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Tienen un fuerte olor dulce, que persiste durante años.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para el nombre del árbol
        TextField(
            value = treeName,
            onValueChange = { treeName = it },
            label = { Text("Nombre del árbol") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de detalles
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Fecha de plantado: 15/08/2023")
            Text("Geo ubicación: 20.1234, -99.1234")
            Text("Características: Madera fuerte y duradera.")
            Text("Rutina de cuidado: Riego semanal.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Adoptar
        Button(
            onClick = onNextButtonClicked,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Adoptar")
        }
    }
}
